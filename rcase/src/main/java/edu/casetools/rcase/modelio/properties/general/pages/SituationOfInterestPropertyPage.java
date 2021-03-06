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
package edu.casetools.rcase.modelio.properties.general.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelio.api.module.propertiesPage.IModulePropertyTable;
import org.modelio.metamodel.uml.infrastructure.Dependency;
import org.modelio.metamodel.uml.infrastructure.ModelElement;

import edu.casetools.rcase.modelio.properties.IPropertyContent;
import edu.casetools.rcase.module.api.RCaseProperties;
import edu.casetools.rcase.module.api.RCaseStereotypes;
import edu.casetools.rcase.module.i18n.I18nMessageService;
import edu.casetools.rcase.module.impl.RCaseModule;
import edu.casetools.rcase.module.impl.RCasePeerModule;
import edu.casetools.rcase.utils.PropertiesUtils;

public class SituationOfInterestPropertyPage implements IPropertyContent {

    @Override
    public void changeProperty(ModelElement element, int row, String value) {

	switch (row) {
	case 1:
	    element.setName(value);
	    break;
	case 2:
	    PropertiesUtils.getInstance().findAndAddValue(RCaseModule.getInstance(), RCasePeerModule.MODULE_NAME,
		    RCaseProperties.PROPERTY_SITUATION_OF_INTEREST_ID, value, element);
	    break;
	case 3:
	    PropertiesUtils.getInstance().findAndAddValue(RCaseModule.getInstance(), RCasePeerModule.MODULE_NAME,
		    RCaseProperties.PROPERTY_SITUATION_OF_INTEREST_DESCRIPTION, value, element);
	    break;
	case 4:
	    PropertiesUtils.getInstance().findAndAddValue(RCaseModule.getInstance(), RCasePeerModule.MODULE_NAME,
		    RCaseProperties.PROPERTY_SITUATION_OF_INTEREST_PRIORITY, value, element);
	    break;
	case 5:
	    PropertiesUtils.getInstance().findAndAddValue(RCaseModule.getInstance(), RCasePeerModule.MODULE_NAME,
		    RCaseProperties.PROPERTY_SITUATION_OF_INTEREST_FREQUENCY, value, element);
	    break;
	default:
	    break;
	}

    }

    @Override
    public void update(ModelElement element, IModulePropertyTable table) {

	table.addProperty(RCaseProperties.PROPERTY_NAME, element.getName());

	String property = PropertiesUtils.getInstance()
		.getTaggedValue(RCaseProperties.PROPERTY_SITUATION_OF_INTEREST_ID, element);
	table.addProperty(I18nMessageService.getString("Ui.SituationOfInterest.Property.TagId"), property);

	property = PropertiesUtils.getInstance()
		.getTaggedValue(RCaseProperties.PROPERTY_SITUATION_OF_INTEREST_DESCRIPTION, element);
	table.addProperty(I18nMessageService.getString("Ui.SituationOfInterest.Property.TagText"), property);
	
	property = PropertiesUtils.getInstance()
			.getTaggedValue(RCaseProperties.PROPERTY_SITUATION_OF_INTEREST_PRIORITY, element);
		table.addProperty(I18nMessageService.getString("Objective.properties.prioritytype"), property,
				new String[] { I18nMessageService.getString("Objective.properties.prioritytype.normal"),
					I18nMessageService.getString("Objective.properties.prioritytype.important"),
					I18nMessageService.getString("Objective.properties.prioritytype.critical") });
	
	property = PropertiesUtils.getInstance()
			.getTaggedValue(RCaseProperties.PROPERTY_SITUATION_OF_INTEREST_FREQUENCY, element);
		table.addProperty(I18nMessageService.getString("Ui.SituationOfInterest.Property.TagFrequency"), property,
				new String[] { I18nMessageService.getString("Ui.SituationOfInterest.Property.TagFrequency.Low"),
					I18nMessageService.getString("Ui.SituationOfInterest.Property.TagFrequency.Medium"),
					I18nMessageService.getString("Ui.SituationOfInterest.Property.TagFrequency.High") });
		
	property = getSOIRecommendationValue(element);
			table.addConsultProperty(I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation"), property);
//				,new String[] { I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.Recommended"),
//					I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.RecommendedWarning"),
//					I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.NotRecommended") });

	checkDependencies(element, table);

    }

    private String getSOIRecommendationValue(ModelElement element) {
    	List<String> recommendations = new ArrayList<>();
		for(Dependency dependency : element.getImpactedDependency()){
			if(dependency.isStereotyped(RCasePeerModule.MODULE_NAME, RCaseStereotypes.STEREOTYPE_CONTEXT_DETECTS)){
				if(dependency.getImpacted().isStereotyped(RCasePeerModule.MODULE_NAME, RCaseStereotypes.STEREOTYPE_SITUATION_DETECTION_PLAN)){
					recommendations.add(SituationDetectionPlanPropertyPage.getSOIDetectionPlanRecommendationValue(dependency.getImpacted()));
				}
			}
		}
		return checkRecommendationValues(recommendations);
	}

	private String checkRecommendationValues(List<String> recommendations) {
		if(recommendations.contains(I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.Recommended"))){
			return I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.Recommended");
		} else if (recommendations.contains(I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.RecommendedWarning"))){
			return I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.Undetermined");
		} else if (recommendations.contains(I18nMessageService.getString(""))){
			return I18nMessageService.getString("");
		}
		return I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.Undetermined");
	}

	private void checkDependencies(ModelElement element, IModulePropertyTable table) {
	checkDependency(RCaseStereotypes.STEREOTYPE_CONTEXT_DETECTS, "Detects", element, table);
	checkDependency(RCaseStereotypes.STEREOTYPE_TRIGGERS, "Triggers", element, table);
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
