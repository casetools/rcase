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
package edu.casetools.rcase.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelio.api.modelio.model.IModelingSession;
import org.modelio.api.modelio.model.IUmlModel;
import org.modelio.api.module.AbstractJavaModule;
import org.modelio.metamodel.mmextensions.infrastructure.ExtensionNotFoundException;
import org.modelio.metamodel.uml.behavior.usecaseModel.UseCase;
import org.modelio.metamodel.uml.infrastructure.Dependency;
import org.modelio.metamodel.uml.infrastructure.ModelElement;
import org.modelio.metamodel.uml.infrastructure.Note;
import org.modelio.metamodel.uml.infrastructure.Stereotype;
import org.modelio.metamodel.uml.statik.Class;
import org.modelio.metamodel.uml.statik.NameSpace;
import org.modelio.metamodel.uml.statik.Package;
import org.modelio.vcore.smkernel.mapi.MObject;

import edu.casetools.rcase.utils.tables.TableUtils;


// TODO: Auto-generated Javadoc
/**
 * The Class DiagramUtils.
 */
public class ElementUtils {
    private static final Logger logger = Logger.getLogger(ElementUtils.class.getName());
    private static ElementUtils instance = null;

    /**
     * Gets the single instance of DiagramUtils.
     *
     * @return single instance of DiagramUtils
     */
    public static ElementUtils getInstance() {
	if (instance == null) {
	    instance = new ElementUtils();
	}
	return instance;
    }

    /**
     * Sets the free name.
     *
     * @param element
     *            the element
     * @param testedName
     *            the tested name
     */
    public void setFreeName(ModelElement element, String testedName) {
	List<MObject> nameList = ModelioUtils.getInstance().getAllElements();
	String extension = "";
	int i = 1;
	while (nameExists(nameList, testedName + extension)) {
	    extension = Integer.toString(i);
	    i++;
	}

	element.setName(testedName + extension);
    }
    
    /**
     * Name exists.
     *
     * @param nameList
     *            the name list
     * @param name
     *            the name
     * @return true, if successful
     */
    public boolean nameExists(List<MObject> nameList, String name) {
	for (MObject object : nameList) {
	    if (object.getName().equals(name))
		return true;
	}
	return false;
    }
    
    /**
     * Sets the free property.
     *
     * @param element
     *            the element
     * @param testedName
     *            the tested name
     */
    public void setFreeProperty(ModelElement element, String moduleName, String stereotypeName, String propertyName) {
	List<MObject> elementsList = new ArrayList<>();
	elementsList = TableUtils.getInstance().getAllElementsStereotypedAs(elementsList, stereotypeName);

	int i = 0;
	while (propertyExists(elementsList, Integer.toString(i), moduleName, propertyName)) {
	    i++;
	}

	try {
	    element.putTagValue(moduleName, propertyName, Integer.toString(i));
	} catch (ExtensionNotFoundException e) {
	    logger.log(Level.SEVERE, e.getMessage(), e);
	}
    }
    
    /**
     * Property exists.
     *
     * @param elementsList
     *            the name list
     * @param name
     *            the name
     * @return true, if successful
     */
    public boolean propertyExists(List<MObject> elementsList, String name, String moduleName, String propertyName) {

	for (MObject object : elementsList) {
	    String propertyValue = ((ModelElement) object).getTagValue(moduleName, propertyName);
	    if (propertyValue != null && propertyValue.equals(name))
		return true;
	}

	return false;
    }



    /**
     * Creates the class.
     *
     * @param selectedElements
     *            the selected elements
     * @param session
     *            the session
     * @param name
     *            the name
     * @param stereotypeName
     *            the stereotype name
     * @return the class
     */
    public Class createClass(AbstractJavaModule module, List<MObject> selectedElements, IModelingSession session, String name,
	    String stereotypeName) {
	Stereotype stereotype = session.getMetamodelExtensions().getStereotype(stereotypeName, module
		.getModuleContext().getModelioServices().getMetamodelService().getMetamodel().getMClass(Class.class));
	for (MObject element : selectedElements) {
	    Class createdElement = session.getModel().createClass(name, (NameSpace) element, stereotype);
	    this.setFreeName(createdElement, name);
	    return createdElement;
	}
	return null;
    }

    /**
     * Creates the use case.
     *
     * @param selectedElements
     *            the selected elements
     * @param session
     *            the session
     * @param name
     *            the name
     * @param stereotypeName
     *            the stereotype name
     * @return the use case
     */
    public UseCase createUseCase(AbstractJavaModule module, List<MObject> selectedElements, IModelingSession session, String name,
	    String stereotypeName) {
	Stereotype stereotype = session.getMetamodelExtensions().getStereotype(stereotypeName, module
		.getModuleContext().getModelioServices().getMetamodelService().getMetamodel().getMClass(UseCase.class));

	for (MObject element : selectedElements) {
	    UseCase createdElement = session.getModel().createUseCase(name, (NameSpace) element, stereotype);
	    this.setFreeName(createdElement, name);
	    return createdElement;
	}
	return null;
    }

    /**
     * Creates the package.
     *
     * @param selectedElements
     *            the selected elements
     * @param session
     *            the session
     * @param name
     *            the name
     * @param stereotypeName
     *            the stereotype name
     * @return the package
     */
    public Package createPackage(AbstractJavaModule module, List<MObject> selectedElements, IModelingSession session, String name,
	    String stereotypeName) {
	Stereotype stereotype = session.getMetamodelExtensions().getStereotype(stereotypeName, module
		.getModuleContext().getModelioServices().getMetamodelService().getMetamodel().getMClass(Package.class));

	for (MObject element : selectedElements) {
	    Package createdElement = session.getModel().createPackage(name, (NameSpace) element, stereotype);
	    this.setFreeName(createdElement, name);
	    return createdElement;
	}
	return null;
    }

    /**
     * Creates the note.
     *
     * @param model
     *            the model
     * @param owner
     *            the owner
     * @param stereotypeName
     *            the stereotype name
     * @return the note
     * @throws ExtensionNotFoundException
     *             the extension not found exception
     */
    public Note createNote(IUmlModel model, String moduleName, ModelElement owner, String stereotypeName)
	    throws ExtensionNotFoundException {
    	return model.createNote(moduleName, stereotypeName, owner, "");
    }

    /**
     * Creates the dependency.
     *
     * @param origin
     *            the origin
     * @param target
     *            the target
     * @param stereotype
     *            the stereotype
     * @return the dependency
     */
    public Dependency createDependency(AbstractJavaModule module, String moduleName, ModelElement origin, ModelElement target, String stereotype) {
	try {
	    Dependency dependency = module.getModuleContext().getModelingSession().getModel()
		    .createDependency(origin, target, moduleName, stereotype);
	    dependency.setName("");
	    return dependency;
	} catch (ExtensionNotFoundException e) {
	    logger.log(Level.SEVERE, e.getMessage(), e);
	}
	return null;
    }
    

}
