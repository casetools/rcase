/*
 * Copyright 2015 @author Unai Alegre 
 * 
 * This file is part of RCASE (Requirements for Context-Aware Systems Engineering), a module 
 * of Modelio that aids the requirements elicitation phase of a Context-Aware System (C-AS). 
 * 
 * RCASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * RCASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Modelio. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.rcase.modelio.properties.pages;

import java.util.ArrayList;
import java.util.Iterator;

import org.modelio.api.module.propertiesPage.IModulePropertyTable;
import org.modelio.metamodel.uml.infrastructure.Dependency;
import org.modelio.metamodel.uml.infrastructure.ModelElement;

import edu.casetools.rcase.modelio.properties.IPropertyContent;
import edu.casetools.rcase.module.api.RCaseProperties;
import edu.casetools.rcase.module.api.RCaseStereotypes;
import edu.casetools.rcase.module.i18n.I18nMessageService;
import edu.casetools.rcase.module.impl.RCasePeerModule;
import edu.casetools.rcase.utils.PropertiesUtils;

public class RequirementPropertyPage implements IPropertyContent {

    @Override
    public void changeProperty(ModelElement element, int row, String value) {

	switch (row) {
	case 1:
	    element.setName(value);
	    break;
	case 2:
	    PropertiesUtils.getInstance().findAndAddValue(RCasePeerModule.MODULE_NAME, RCaseProperties.PROPERTY_REQUIREMENT_ID,
		    value, element);
	    break;
	case 3:
	    PropertiesUtils.getInstance().findAndAddValue(RCasePeerModule.MODULE_NAME,
		    RCaseProperties.PROPERTY_REQUIREMENT_DESCRIPTION, value, element);
	    break;
	default:
	    break;
	}

    }

    @Override
    public void update(ModelElement element, IModulePropertyTable table) {

	table.addProperty(RCaseProperties.PROPERTY_NAME, element.getName());

	String property = PropertiesUtils.getInstance().getTaggedValue(RCaseProperties.PROPERTY_REQUIREMENT_ID,
		element);
	table.addProperty(I18nMessageService.getString("Ui.Requirement.Property.TagId"), property);

	property = PropertiesUtils.getInstance().getTaggedValue(RCaseProperties.PROPERTY_REQUIREMENT_DESCRIPTION,
		element);
	table.addProperty(I18nMessageService.getString("Ui.Requirement.Property.TagText"), property);

	checkDependencies(element, table);

    }

    private void checkDependencies(ModelElement element, IModulePropertyTable table) {
	checkDependency(RCaseStereotypes.STEREOTYPE_DERIVE, "Derive", element, table);
	checkDependency(RCaseStereotypes.STEREOTYPE_COPY, "Copy", element, table);
	checkDependency(RCaseStereotypes.STEREOTYPE_PART, "Part", element, table);
	checkDependency(RCaseStereotypes.STEREOTYPE_REFINE, "Refine", element, table);
	checkDependency(RCaseStereotypes.STEREOTYPE_SATISFY, "Satisfy", element, table);
	checkDependency(RCaseStereotypes.STEREOTYPE_VERIFY, "Verify", element, table);
    }

    private void checkDependency(String stereotype, String name, ModelElement element, IModulePropertyTable table) {
	ArrayList<ModelElement> value = new ArrayList<>();
	String empty = "";

	for (Iterator<?> localIterator = element.getImpactedDependency().iterator(); localIterator.hasNext();) {
	    Dependency dependency = (Dependency) localIterator.next();
	    if (dependency.isStereotyped(RCasePeerModule.MODULE_NAME, stereotype))
		value.add(dependency.getImpacted());
	}

	String valuetab = PropertiesUtils.getInstance().getAbsoluteNamesWithSeparator(value);
	if (!valuetab.equals(empty)) {
	    table.addConsultProperty(I18nMessageService.getString("Ui." + name + ".From"), valuetab);
	}

	value = new ArrayList<>();
	for (Dependency dependency : element.getDependsOnDependency()) {
	    if (dependency.isStereotyped(RCasePeerModule.MODULE_NAME, stereotype))
		value.add(dependency.getDependsOn());
	}
	valuetab = PropertiesUtils.getInstance().getAbsoluteNamesWithSeparator(value);
	if (!valuetab.equals(empty)) {
	    table.addConsultProperty(I18nMessageService.getString("Ui." + name + ".To"), valuetab);
	}
    }

}
