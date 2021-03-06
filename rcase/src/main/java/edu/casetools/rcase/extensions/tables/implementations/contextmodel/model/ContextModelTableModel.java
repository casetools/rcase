/*
 * Copyright 2015 @author Unai Alegre 
 * 
 * This file is part of RCASE (Requirements for Context-Aware Systems Engineering), a module 
 * of Modelio that aids the requirements elicitation stage of a Context-Aware System (C-AS). 
 * 
 * RCASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * RCASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with RCASE.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.rcase.extensions.tables.implementations.contextmodel.model;

import javax.swing.table.AbstractTableModel;

// TODO: Auto-generated Javadoc
/**
 * The Class DependencyTableModel.
 */
public class ContextModelTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 4329075668436833054L;
    private ContextModelTableData data;
    // private ContextModelTableDataHandler dataHandler;

    /**
     * Instantiates a new dependency table model.
     */
    public ContextModelTableModel() {
	this.data = new ContextModelTableData();
	// this.dataHandler = new ContextModelTableDataHandler();
    }

    /**
     * Instantiates a new dependency table model.
     *
     * @param data
     *            the data
     */
    public ContextModelTableModel(ContextModelTableData data) {
	this.data = data;
	// this.dataHandler = new ContextModelTableDataHandler();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    @Override
    public int getColumnCount() {
	return this.data.getColumnCount();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
     */
    @Override
    public boolean isCellEditable(int row, int column) {
	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
     * int, int)
     */
    @Override
    public void setValueAt(Object value, int row, int column) {
	ContextModelTableRow valueAt = this.data.getDataList().get(row);
	valueAt.set(column, value);
	fireTableCellUpdated(row, column);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getRowCount()
     */
    @Override
    public int getRowCount() {
	return this.data.getRowCount();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
	return this.data.getHeaders().get(column).getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int column) {
	return String.class;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    @Override
    public Object getValueAt(int row, int column) {
	ContextModelTableRow valueAt = this.data.getDataList().get(row);
	if (valueAt != null)
	    return valueAt.get(column).toString();
	return new Object();
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public ContextModelTableData getData() {
	return this.data;
    }

    /**
     * Refresh.
     */
    public void refresh() {
	int oldSize = this.data.newDataList();
	// this.data = this.dataHandler.updateHeaders(this.data);
	this.data.update();
	this.refresh(oldSize);
    }

    private void refresh(int oldSize) {
	int oldSizeAuxiliar = oldSize;
	if (oldSizeAuxiliar > this.data.getDataList().size())
	    oldSizeAuxiliar = this.data.getDataList().size();
	refreshExistentValues(oldSizeAuxiliar);
	if (this.data.getDataList().size() > oldSizeAuxiliar)
	    createNewValues(oldSizeAuxiliar);
    }

    private void refreshExistentValues(int oldSize) {
	for (int row = 0; row < oldSize; row++) {
	    for (int column = 0; column < this.data.getDataList().get(row).size(); column++) {
		this.setValueAt(this.data.getDataList().get(row).get(column), row, column);
	    }
	}
    }

    private void createNewValues(int oldSize) {
	for (int row = oldSize; row < data.getDataList().size(); row++) {
	    fireTableRowsInserted(oldSize - 1, data.getDataList().size() - 1);
	}
    }

}
