/*
 * Copyright 2015 @author Unai Alegre 
 * 
 * This file is part of R-CASE (Requirements for Context-Aware Systems Engineering), a module 
 * of Modelio that aids the requirements elicitation phase of a Context-Aware System (C-AS). 
 * 
 * R-CASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * R-CASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Modelio.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.rcase.utils.tables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.modelio.api.modelio.model.IMetamodelExtensions;
import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.modelio.model.ITransaction;
import org.modelio.api.modelio.model.IUmlModel;
import org.modelio.api.module.AbstractJavaModule;
import org.modelio.metamodel.mmextensions.infrastructure.ExtensionNotFoundException;
import org.modelio.metamodel.uml.infrastructure.Dependency;
import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.metamodel.uml.infrastructure.Stereotype;
import org.modelio.metamodel.uml.infrastructure.UmlModelElement;
import org.modelio.metamodel.uml.statik.BindableInstance;
import org.modelio.metamodel.uml.statik.Connector;
import org.modelio.metamodel.uml.statik.ConnectorEnd;
import org.modelio.metamodel.uml.statik.Link;
import org.modelio.vcore.smkernel.mapi.MObject;

import edu.casetools.rcase.extensions.tables.implementations.traceability.model.DependencyTableData;
import edu.casetools.rcase.utils.ModelioUtils;
import edu.casetools.rcase.utils.ResourcesManager;

/**
 * The Class ModelioTableUtils .
 */
public class ModelioTableUtils {

    private static ModelioTableUtils instance = null;

    /**
     * Gets the single instance of ModelioTableUtils.
     *
     * @return single instance of ModelioTableUtils
     */
    public static ModelioTableUtils getInstance() {
	if (instance == null) {
	    instance = new ModelioTableUtils();
	}
	return instance;
    }

    /**
     * Gets the table values.
     *
     * @param col
     *            the col
     * @param row
     *            the row
     * @param data
     *            the data
     * @return the table values
     */
    public Object getTableValues(Object col, Object row, DependencyTableData data) {
	ModelElement source = (ModelElement) row;
	ModelElement target = (ModelElement) col;
	String result = "";

	if (data.getLinkStereotype().getBaseClassName().equals(Dependency.class.getSimpleName())) {
	    result = getSingleRelationship(source, target, data, result);
	} else if (data.getLinkStereotype().getBaseClassName().equals(Connector.class.getSimpleName())) {
	    result = getDoubleRelationship(source, target, data, result);
	}

	return result;
    }

    private String getSingleRelationship(ModelElement source, ModelElement target, DependencyTableData data,
	    String result) {

	for (Dependency dp : source.getDependsOnDependency()) {
	    result = getDependsOn(dp, target, data, result, "->");
	}
	for (Dependency invdp : target.getDependsOnDependency()) {
	    result = getDependsOn(invdp, source, data, result, "<-");
	}
	return result;
    }

    /**
     * Removes the requirement.
     *
     * @param requirement
     *            the requirement
     */
    public void removeRequirement(AbstractJavaModule module, MObject requirement) {
	IModelingSession session = module.getModuleContext().getModelingSession();
	ITransaction transaction = session
		.createTransaction("Create");
	requirement.delete();
	transaction.commit();
	transaction.close();

    }

    private String getDependsOn(Dependency dependency, ModelElement target, DependencyTableData data, String result,
	    String value) {

	if ((dependency.isStereotyped(data.getLinkStereotype().getModule().getName(),
		data.getLinkStereotype().getName())) && (target.equals(dependency.getDependsOn()))) {
	    result = result + value;
	}

	return result;
    }

    private String getDoubleRelationship(ModelElement source, ModelElement target, DependencyTableData data,
	    String result) {
    	if(source instanceof UmlModelElement){
			for (ConnectorEnd end : ((UmlModelElement) source).getRepresentingEnd()) {
			    Link link = end.getLink();
			    if ((null != link) && (link.isStereotyped(data.getLinkStereotype().getModule().getName(),
				    data.getLinkStereotype().getName()))) {
				if (target.equals(end.getOppositeOwner())) {
				    if (end.isNavigable()) {
					result = result + "->";
				    }
				    if (end.getOpposite().isNavigable()) {
					result = result + "<-";
				    }
				}
			}
		}
	}
	return result;
    }

    /**
     * Gets the possible values.
     *
     * @return the possible values
     */
    public Vector<String> getPossibleValues() {
	Vector<String> result = new Vector<String>();
	result.add("->");
	result.add("<-");
	result.add("<->");
	result.add("");
	return result;
    }

    /**
     * Sets the val.
     *
     * @param col
     *            the col
     * @param row
     *            the row
     * @param depth
     *            the depth
     * @param value
     *            the value
     */
    public void setVal(AbstractJavaModule module, Object col, Object row, Object depth, Object value) {
	IModelingSession session = module.getModuleContext().getModelingSession();
	IUmlModel model = session.getModel();
	ModelElement source = (ModelElement) row;
	ModelElement target = (ModelElement) col;
	Stereotype ster = (Stereotype) depth;

	ITransaction transaction = session
		.createTransaction("Create");
	if (ster.getBaseClassName().equals(Dependency.class.getSimpleName())) {
	    setDependencyValue(model, target, source, ster, value);
	} else if (ster.getBaseClassName().equals(Connector.class.getSimpleName())) {
	    setConnectorValue(module, model, target, source, ster, value);
	}

	transaction.commit();
	transaction.close();

    }

    private void setDependencyValue(IUmlModel model, ModelElement target, ModelElement source, Stereotype ster,
	    Object value) {
	Dependency link = null;

	link = createLink(target, source, ster, link);
	createDependency(model, target, source, ster, value, link, "->");
	link = createInvLink(target, source, ster);
	createDependency(model, source, target, ster, value, link, "<-");
    }

    private Dependency createInvLink(ModelElement target, ModelElement source, Stereotype ster) {
	Dependency link = null;
	for (Iterator<?> e = target.getDependsOnDependency().iterator(); ((Iterator<?>) e).hasNext();) {
	    Dependency invdp = (Dependency) ((Iterator<?>) e).next();
	    if (invdp.isStereotyped(ster.getModule().getName(), ster.getName())) {
		if (source.equals(invdp.getDependsOn())) {
		    link = invdp;
		}
	    }

	}
	return link;
    }

    private Dependency createLink(ModelElement target, ModelElement source, Stereotype ster, Dependency link) {
	for (Dependency dp : source.getDependsOnDependency()) {
	    if ((dp.isStereotyped(ster.getModule().getName(), ster.getName())) && (target.equals(dp.getDependsOn()))) {
		link = dp;
	    }

	}
	return link;
    }

    private void createDependency(IUmlModel model, ModelElement target, ModelElement source, Stereotype ster,
	    Object value, Dependency link, String relation) {
	if ((null == link) && ((value.equals(relation)) || (value.equals("<->"))))
	    try {
		model.createDependency(source, target, ster.getModule().getName(), ster.getName());
	    } catch (ExtensionNotFoundException e) {
		e.printStackTrace();
	    }
	else if ((null != link) && (!value.equals(relation)) && (!value.equals("<->"))) {
	    link.delete();
	}
    }

    private void setConnectorValue(AbstractJavaModule module, IUmlModel model, ModelElement target, ModelElement source, Stereotype ster,
	    Object value) {
	ConnectorEnd fend = null;
	fend = createConnectorEnd(target, source, ster, fend);

	if ((null == fend) && ((value.equals("->")) || (value.equals("<->")) || (value.equals("<-")))) {
	    createConnectorValue(module, model, target, source, ster, value);
	} else if ((null != fend) && (value.equals(""))) {
	    fend.getOpposite().delete();
	    fend.getLink().delete();
	    fend.delete();
	} else if ((fend.isNavigable()) && (!value.equals("->"))) {
	    fend.setNavigable(false);
	} else if ((fend.getOpposite().isNavigable()) && (!value.equals("<-"))) {
	    fend.getOpposite().setNavigable(false);
	}
    }

    private void createConnectorValue(AbstractJavaModule module, IUmlModel model, ModelElement target, ModelElement source, Stereotype ster,
	    Object value) {
	IMetamodelExtensions extensions = module.getModuleContext().getModelingSession()
		.getMetamodelExtensions();
	Connector co = model.createConnector((BindableInstance) source, (BindableInstance) target, "");
	Stereotype coster = extensions.getStereotype(ster.getModule().getName(), ster.getName(),
		module.getModuleContext().getModelioServices().getMetamodelService().getMetamodel()
			.getMClass(Connector.class));
	setNavigableLinks(ster, value, co, coster);
    }

    private ConnectorEnd createConnectorEnd(ModelElement target, ModelElement source, Stereotype ster,
	    ConnectorEnd fend) {
    	
    	if(source instanceof UmlModelElement){
			for (Iterator<?> e = ((UmlModelElement) source).getRepresentingEnd().iterator(); ((Iterator<?>) e).hasNext();) {
			    ConnectorEnd end = (ConnectorEnd) ((Iterator<?>) e).next();
			    Link link = end.getLink();
		
			    if ((null != link) && (link.isStereotyped(ster.getModule().getName(), ster.getName()))
				    && (target.equals(end.getOppositeOwner()))) {
				fend = end;
			    }
		}

	}
	return fend;
    }

    private void setNavigableLinks(Stereotype ster, Object value, Connector co, Stereotype coster) {
	if (null != ster) {
	    co.getExtension().add(coster);
	}

	if ((value.equals("->")) || (value.equals("<->"))) {
	    co.getLinkEnd().get(0).setNavigable(true);
	    co.getLinkEnd().get(1).setNavigable(false);
	}

	if ((value.equals("<-")) || (value.equals("<->"))) {
	    co.getLinkEnd().get(0).setNavigable(false);
	    co.getLinkEnd().get(1).setNavigable(true);
	}

	if (value.equals("<->")) {
	    co.getLinkEnd().get(0).setNavigable(true);
	    co.getLinkEnd().get(1).setNavigable(true);
	}
    }

    /**
     * Creates the j label.
     *
     * @param elementName
     *            the element name
     * @return the j label
     */
    public JLabel createJLabel(AbstractJavaModule module, String elementName) {
	Stereotype stereotype;
	ImageIcon icon;
	JLabel label = new JLabel();
	label.setText(elementName);
	label.setOpaque(true);

	stereotype = ModelioUtils.getInstance().getStereotypeOfElementByName(module, elementName);
	if (stereotype != null) {
	    icon = ResourcesManager.getInstance().createImageIcon(stereotype.getIcon());
	    label.setIcon(icon);
	}

	return label;
    }

    public ArrayList<MObject> getContextAttributesFromSituationOfInterest(String moduleName, ModelElement element, String stereotypeName) {
	ArrayList<MObject> list = new ArrayList<>();
	for (Iterator<?> localIterator = element.getImpactedDependency().iterator(); localIterator.hasNext();) {
	    Dependency dependency = (Dependency) localIterator.next();
	    if (dependency.isStereotyped(moduleName, stereotypeName)) {
		list.add(dependency.getImpacted());
	    }
	}

	return list;

    }

}
