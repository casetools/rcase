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
package edu.casetools.rcase.modelio.diagrams.situations.tools.relations;

import org.modelio.api.modelio.diagram.IDiagramGraphic;
import org.modelio.api.modelio.diagram.IDiagramHandle;
import org.modelio.metamodel.uml.infrastructure.Dependency;
import org.modelio.metamodel.uml.infrastructure.ModelElement;

import edu.casetools.rcase.modelio.diagrams.RelationTool;
import edu.casetools.rcase.module.api.RCaseStereotypes;
import edu.casetools.rcase.module.impl.RCaseModule;
import edu.casetools.rcase.module.impl.RCasePeerModule;
import edu.casetools.rcase.utils.ElementUtils;

/**
 * The Class ArisesTool is the tool for creating an Arise Dependency
 * relation.
 */
public class ArisesTool extends RelationTool {

	private boolean isSituationOfInterest;
	
	public ArisesTool(){
		this.isSituationOfInterest = false;
	}
    /*
     * (non-Javadoc)
     * 
     * @see
     * org.modelio.api.diagram.tools.DefaultLinkTool#acceptFirstElement(org.
     * modelio.api.diagram.IDiagramHandle,
     * org.modelio.api.diagram.IDiagramGraphic)
     */
    @Override
    public boolean acceptFirstElement(IDiagramHandle representation, IDiagramGraphic target) {
		ModelElement element = (ModelElement) target.getElement();
		isSituationOfInterest = element.isStereotyped(RCasePeerModule.MODULE_NAME, RCaseStereotypes.STEREOTYPE_SITUATION_OF_INTEREST);
		return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.modelio.api.diagram.tools.DefaultLinkTool#acceptSecondElement(org
     * .modelio.api.diagram.IDiagramHandle,
     * org.modelio.api.diagram.IDiagramGraphic,
     * org.modelio.api.diagram.IDiagramGraphic)
     */
    @Override
    public boolean acceptSecondElement(IDiagramHandle representation, IDiagramGraphic source, IDiagramGraphic target) {
    	ModelElement element = (ModelElement) target.getElement();
    	if(isSituationOfInterest)
    		return true;
    	else
    		return element.isStereotyped(RCasePeerModule.MODULE_NAME, RCaseStereotypes.STEREOTYPE_SITUATION_OF_INTEREST);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.casesuite.modelio.diagrams.RelationTool#
     * createDependency(org.modelio.metamodel.uml.infrastructure.ModelElement,
     * org.modelio.metamodel.uml.infrastructure.ModelElement)
     */
    @Override
    public Dependency createDependency(ModelElement originElement, ModelElement targetElement) {
	return ElementUtils.getInstance().createDependency(RCaseModule.getInstance(), RCasePeerModule.MODULE_NAME, originElement, targetElement,
		RCaseStereotypes.STEREOTYPE_ARISES);
    }

}