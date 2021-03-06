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
package edu.casetools.rcase.extensions.tables.implementations.traceability.view.edit;

import java.util.List;

import org.modelio.metamodel.uml.infrastructure.Stereotype;

import edu.casetools.rcase.extensions.tables.implementations.traceability.DependencyTable;
import edu.casetools.rcase.module.api.RCaseView;
import edu.casetools.rcase.module.i18n.I18nMessageService;

// TODO: Auto-generated Javadoc
/**
 * The Class EditRowDialog.
 */
public class EditRowDialog extends EditDialog {

    private static final long serialVersionUID = 496286896936656720L;

    /**
     * Instantiates a new edits the row dialog.
     *
     * @param main
     *            the main
     */
    public EditRowDialog(DependencyTable main) {
	super(main, I18nMessageService.getString("Dialogs.Name.Row"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.casesuite.extensions.tables.traceability.view.edit.
     * EditDialog#getFilterStereotypes()
     */
    @Override
    protected List<Stereotype> getFilterStereotypes() {
	return this.data.getyStereotypeFilter();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.casesuite.extensions.tables.traceability.view.edit.
     * EditDialog#getStereotypes()
     */
    @Override
    protected List<Stereotype> getStereotypes() {
	return this.data.getyStereotypes();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.casesuite.extensions.tables.traceability.view.edit.
     * EditDialog#setOkActionCommand()
     */
    @Override
    protected void setOkActionCommand() {
	ok.setActionCommand(RCaseView.OK_ROW);
    }

}
