package name.cphillipson.experimental.gwt.shared.bean;

import java.io.Serializable;

// A simple universal data row container somewhat inspired by .NET ADO
public class DataRow implements Serializable {

    private DataTable m_table;
    private String m_data[];

    public DataRow() {
    }

    public DataRow(DataTable table) {
        // A DataRow must always belongs to a table (the table contains the schema, hence number of columns)
        m_table = table;
        m_data = new String[m_table.getColumnCount()];
    }

    public void setDataTable(DataTable table) {
        // A DataRow must always belongs to a table (the table contains the schema, hence number of columns)
        m_table = table;
        m_data = new String[m_table.getColumnCount()];
    }

    public int getColumnCount() {
        return m_table.getColumnCount();
    }

    public String get(String columnName) {
        int index = m_table.getColumnIndex(columnName);
        return m_data[index];
    }

    public String get(int index) {
        return m_data[index];
    }

    public void set(int index, String object) {
        m_data[index] = object;
    }

    public void set(String columnName, String object) {
        m_data[m_table.getColumnIndex(columnName)] = object;
    }
}
