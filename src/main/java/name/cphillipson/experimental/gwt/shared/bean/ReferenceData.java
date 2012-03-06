package name.cphillipson.experimental.gwt.shared.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

/**
 * Key-value pairs of reference data to be used with a single or multi-select list UI component; e.g., GWT <code>ListBox</code>.
 * @author cphillipson
 *
 */
public class ReferenceData implements Serializable {

    /** Key is what is to be presented and value is what gets submitted **/
    private Map<String, String> data;

    public ReferenceData() {
        data = new TreeMap<String, String>();
    }

    // convenience: use when you want key and value to be the same
    public void addData(String value) {
        data.put(value,  value);
    }

    public void addData(String displayValue, String submitValue) {
        data.put(displayValue,  submitValue);
    }

    public Map<String, String> allData() {
        return Collections.unmodifiableMap(data);
    }

    public String getDisplayValueForSubmitValue(String submitValue) {
        String result = null;
        for (final Map.Entry<String, String> e: data.entrySet()) {
            if (submitValue.equals(e.getValue())) {
                result = e.getKey();
                break;
            }
        }
        return result;
    }

}
