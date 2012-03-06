package name.cphillipson.experimental.gwt.shared.bean;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TableMetadata {

    private Map<String, ReferenceData> metadata;

    public TableMetadata() {
        metadata = new LinkedHashMap<String, ReferenceData>();
    }

    public int getColumnIndex(String columnName) {
        final List<String> columnNames = new LinkedList(metadata.keySet());
        return columnNames.indexOf(columnName);
    }

    public int getTotalNumberOfColumns() {
        return metadata.keySet().size();
    }

    // use only when display values are the same as submitted values
    public void addColumnMetadata(String columnName, String[] optionValues) {
        addColumnMetadata(columnName, optionValues, optionValues);
    }

    public void addColumnMetadata(String columnName, String[] optionDisplayValues, String[] optionSubmitValues) {
        if (optionDisplayValues == null || optionSubmitValues == null) {
            throw new IllegalArgumentException("Reference data display and submit values must not be null.");
        }
        if (optionDisplayValues.length != optionSubmitValues.length) {
            throw new IllegalArgumentException("There must be the same number of option display and submit values.");
        }
        final ReferenceData rd = new ReferenceData();
        for (int i = 0; i < optionDisplayValues.length; i++) {
            rd.addData(optionDisplayValues[i], optionSubmitValues[i]);
        }
        addColumnMetadata(columnName, rd);
    }

    public void addColumnMetadata(String columnName, ReferenceData refData) {
        metadata.put(columnName,  refData);
    }

    public Map<String, ReferenceData> allColumnMetadata() {
        return Collections.unmodifiableMap(metadata);
    }

}
