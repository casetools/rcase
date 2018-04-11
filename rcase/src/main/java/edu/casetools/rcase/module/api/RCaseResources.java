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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Modelio. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.rcase.module.api;

/**
 * The Class RCaseResoruces contains the name of Styles and Icon resources.
 */

public class RCaseResources {
	
    /****************************************************************************
     * Styles
     **************************************************************************/
    public static final String STYLE_CONTEXT_DIAGRAM 				= "context_dependency_diagram.style";
    public static final String STYLE_REQUIREMENTS_DIAGRAM 			= "requirements_diagram.style";
    public static final String STYLE_USECASE_DIAGRAM 				= "use_case_diagram.style";
	public static final String STYLE_STAKEHOLDER_DIAGRAM 			= "stakeholder_diagram.style";
	public static final String STYLE_OBJECTIVE_DIAGRAM 			    = "objective_diagram.style";
	public static final String STYLE_SITUATIONS_OF_INTEREST_DIAGRAM = "situations_of_interest_diagram.style";

    /****************************************************************************
     * Icons
     **************************************************************************/

    public static final String ICON_DEPENDENCY_TABLE = "res/icons/matrix_16.png";
    public static final String ICON_CONTAINER_TABLE = ICON_DEPENDENCY_TABLE;
    public static final String ICON_CONTEXT_MODEL_TABLE = "res/icons/matrix_16.png";
    public static final String ICON_MODULE = "/res/icons/module_16.png";

    private RCaseResources() {

    }

}
