package name.cphillipson.experimental.gwt.shared.bean;

import java.io.Serializable;

// A simple universal data column container somewhat inspired by .NET ADO
public class DataColumn implements Serializable {

    private String m_name;

    public DataColumn() {
    }

    public DataColumn(String name) {
        // this(name, String.class);
        m_name = name;
    }

    public void setName(String name) {
        m_name = name;
    }

    public String getName() {
        return m_name;
    }

}
