/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.meet;

import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.table.TableModel;
import com.codename1.ui.util.EventDispatcher;
import java.util.ArrayList;

/**
 *
 * @author α Ω
 */
public class MeetTableModel implements TableModel {

private ArrayList<Object[]> data;
private String[] columnNames;
private EventDispatcher dispatcher = new EventDispatcher();
private boolean editable;

public MeetTableModel(String[] columnNames, Object[][] data) {
    this(columnNames, data, false);
}

public MeetTableModel(String[] columnNames, Object[][] data, boolean editable) {
    this.data = new ArrayList<Object[]>();
    for(Object[] o : data) {
        this.data.add(o);
    }
    this.columnNames = columnNames;
    this.editable = editable;
}

public int getRowCount() {
    return data.size();
}

public int getColumnCount() {
    return columnNames.length;
}

public String getColumnName(int i) {
    return columnNames[i];
}

public boolean isCellEditable(int row, int column) {
    return editable;
}

public Object getValueAt(int row, int column) {
    try {
        return data.get(row)[column];
    } catch(ArrayIndexOutOfBoundsException err) {
        return "";
    }
}

public void setValueAt(int row, int column, Object o) {
    data.get(row)[column] = o;
    dispatcher.fireDataChangeEvent(column, row);
}

public void addDataChangeListener(DataChangedListener d) {
    dispatcher.addListener(d);
}

public void removeDataChangeListener(DataChangedListener d) {
    dispatcher.removeListener(d);
}

public void addRow(Object[] rowData, int row) {
    try {
        data.add(rowData);
        dispatcher.fireDataChangeEvent(0, row);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
