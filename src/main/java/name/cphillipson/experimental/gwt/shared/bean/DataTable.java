package name.cphillipson.experimental.gwt.shared.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



// A simple universal data table container somewhat inspired by .NET ADO
public class DataTable implements Serializable {

    // Ordered columns
    private final ArrayList<DataColumn> m_columns = new ArrayList<DataColumn>();
    // Maps column name to column position
    private final HashMap<String, Integer> m_columnMap = new HashMap<String, Integer>();

    // DataTable-wide properties
    private final HashMap<String, String> m_columnHints = new HashMap<String, String>();
    private final HashMap<String, ReferenceData> m_columnRefData = new HashMap<String, ReferenceData>();

    private final ArrayList<DataRow> m_rows = new ArrayList<DataRow>();

    public DataTable() {
    }

    // Table schema setup

    public void addColumn(DataColumn column) {
        m_columns.add(column);
        m_columnMap.put(column.getName(), new Integer(m_columns.size() - 1));
    }

    public void addColumns(String... columnNames) {
        for (final String columnName : columnNames) {
            final DataColumn dataColumn = new DataColumn();
            dataColumn.setName(columnName);
            addColumn(dataColumn);
        }
    }

    public int getColumnCount() {
        return m_columns.size();
    }

    public String getColumnName(int columnIndex) {
        final DataColumn column = m_columns.get(columnIndex);
        return column.getName();
    }

    public String[] getHeader() {
        final ArrayList<String> header = new ArrayList<String>();
        for (final DataColumn heading: m_columns) {
            header.add(heading.getName());
        }
        return header.toArray(new String[header.size()]);
    }



    // Table row access

    public DataRow newRow() {
        // return new DataRow(this);
        final DataRow row = new DataRow();
        row.setDataTable(this);
        return row;
    }

    public void addRow(DataRow row) {
        m_rows.add(row);
    }

    public void addRow(String... columnValue) {
        if (columnValue.length > this.getColumnCount()) {
            throw new RuntimeException("Number of column values exceeds data table's column size constraint.");
        }
        final DataRow row = newRow();
        int index = 0;
        for (final String v: columnValue) {
            row.set(index, v);
            index++;
        }
        m_rows.add(row);
    }

    // accepts a Map of key-value pairings where key is a column name
    // typically used with org.apache.commons.beanutils.BeanMap
    // note: each column value must be a String
    public void addRow(Map<String, String> keyValuePairings) {
        if (keyValuePairings.size() > this.getColumnCount()) {
            throw new RuntimeException("Number of column values exceeds data table's column size constraint.");
        }
        final DataRow row = newRow();
        for (final Map.Entry<String, String> e: keyValuePairings.entrySet()) {
            row.set(getColumnIndex(e.getKey()), e.getValue());
        }
        m_rows.add(row);
    }

    public int getRowCount() {
        return m_rows.size();
    }

    public List<DataRow> allRows() {
        return m_rows;
    }

    public DataRow getRow(int i) {
        return m_rows.get(i);
    }

    public String get(int rowIndex, int columnIndex) {
        final DataRow row = m_rows.get(rowIndex);
        return row.get(columnIndex);
    }

    public String get(int rowIndex, String columnName) {
        return get(rowIndex, getColumnIndex(columnName));
    }

    public int getColumnIndex(String columnName) {
        final Integer i = m_columnMap.get(columnName);

        if (i == null) {
            throw new RuntimeException("Column " + columnName + " has not been defined for the data table.");
        } else {
            return i.intValue();
        }
    }

    // Properties

    public void setColumnHint(String columnName, String hint) {
        m_columnHints.put(columnName, hint);
    }

    public String getColumnHint(String columnName) {
        return m_columnHints.get(columnName);
    }

    public void setColumnReferenceData(String columnName, ReferenceData data) {
        m_columnRefData.put(columnName,  data);
    }

    public ReferenceData getColumnReferenceData(String columnName) {
        return m_columnRefData.get(columnName);
    }


    public Object[][] toObjectArray() {
        final int rows = getRowCount();
        final int columns = getColumnCount();
        final Object[][] result = new Object[rows][columns];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                result[r][c] = get(r, c);
            }
        }
        return result;
    }

}
