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

public class ContextAwareFeaturePropertyPage implements IPropertyContent {

    @Override
    public void changeProperty(ModelElement element, int row, String value) {

	switch (row) {
		case 1:
		    PropertiesUtils.getInstance().findAndAddValue(RCaseModule.getInstance(), RCasePeerModule.MODULE_NAME,
			    RCaseProperties.PROPERTY_CONTEXT_AWARE_FEATURE_TYPE, value, element);
		    break;
		case 2:
		    PropertiesUtils.getInstance().findAndAddValue(RCaseModule.getInstance(), RCasePeerModule.MODULE_NAME,
			    RCaseProperties.PROPERTY_CONTEXT_AWARE_FEATURE_INTERACTION, value, element);
		    break;
		case 3:
		    PropertiesUtils.getInstance().findAndAddValue(RCaseModule.getInstance(), RCasePeerModule.MODULE_NAME,
			    RCaseProperties.PROPERTY_CONTEXT_AWARE_FEATURE_COST, value, element);
		    break;
		case 4:
		    PropertiesUtils.getInstance().findAndAddValue(RCaseModule.getInstance(), RCasePeerModule.MODULE_NAME,
			    RCaseProperties.PROPERTY_CONTEXT_AWARE_RECOMMENDATION, value, element);
		    break;
		default:
		    break;
	}

    }

    @Override
    public void update(ModelElement element, IModulePropertyTable table) {

	String property = PropertiesUtils.getInstance()
			.getTaggedValue(RCaseProperties.PROPERTY_CONTEXT_AWARE_FEATURE_TYPE, element);
		table.addProperty(I18nMessageService.getString("Ui.ContextAwareFeature.Property.TagType"), property,
				new String[] { I18nMessageService.getString("Ui.ContextAwareFeature.Property.TagType.Info"),
					I18nMessageService.getString("Ui.ContextAwareFeature.Property.TagType.Execute"),
					I18nMessageService.getString("Ui.ContextAwareFeature.Property.TagType.Adapt"),
					I18nMessageService.getString("Ui.ContextAwareFeature.Property.TagType.TagInfo") });
	
	property = PropertiesUtils.getInstance()
			.getTaggedValue(RCaseProperties.PROPERTY_CONTEXT_AWARE_FEATURE_INTERACTION, element);
		table.addProperty(I18nMessageService.getString("Ui.ContextAwareFeature.Property.TagInteraction"), property,
				new String[] { I18nMessageService.getString("Ui.ContextAwareFeature.Property.TagInteraction.Active"),
					I18nMessageService.getString("Ui.ContextAwareFeature.Property.TagInteraction.Passive") });
		
	property = PropertiesUtils.getInstance()
			.getTaggedValue(RCaseProperties.PROPERTY_CONTEXT_AWARE_FEATURE_COST, element);
		table.addProperty(I18nMessageService.getString("Ui.ContextAwareFeature.Property.TagCost"), property,
				new String[] { I18nMessageService.getString("Ui.SituationOfInterest.Property.TagFrequency.Low"),
					I18nMessageService.getString("Ui.SituationOfInterest.Property.TagFrequency.Medium"),
					I18nMessageService.getString("Ui.SituationOfInterest.Property.TagFrequency.High") });
		
	property = PropertiesUtils.getInstance()
			.getTaggedValue(RCaseProperties.PROPERTY_SITUATION_OF_INTEREST_RECOMMENDATION, element);
		table.addConsultProperty(I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation"), property);
//				,new String[] { I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.Recommended"),
//					I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.RecommendedWarning"),
//					I18nMessageService.getString("Ui.SituationOfInterest.Property.TagRecommendation.NotRecommended") });

	checkDependencies(element, table);

    }

    private void checkDependencies(ModelElement element, IModulePropertyTable table) {
	checkDependency(RCaseStereotypes.STEREOTYPE_ARISES, "Arises", element, table);
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
