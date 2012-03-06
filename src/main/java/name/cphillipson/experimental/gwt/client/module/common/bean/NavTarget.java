package name.cphillipson.experimental.gwt.client.module.common.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import name.cphillipson.experimental.gwt.shared.bean.Navigable;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;
import name.cphillipson.experimental.gwt.shared.bean.Operation;

/**
 * Encapsulates a <code>Navigable</code>'s param names and values
 * @author cphillipson
 *
 */
public class NavTarget implements Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, String> params = new LinkedHashMap<String, String>();

    public NavTarget() {
        // because GWT's serialization rules say I must have a zero-argument constructor
    }

    private NavTarget(Map<String, String> params) {
        this.params = params;
    }

    public NavTarget(String operation, Map<String, String> params) {
        this(params);
        if (operation != null) {
            addParamAndValue(Navigable.OP_KEY, operation);
        }
    }

    public NavTarget(String[] paramNames, String[] paramValues) {
        addParamsAndValues(paramNames, paramValues);
    }

    private void addParamAndValue(String paramName, String paramValue) {
        // TODO validate?  e.g., make sure paramName does not contain spaces or illegal characters
        getParams().put(paramName, paramValue);
    }

    private void addParamsAndValues(String[] paramNames, String[] paramValues) {
        if (paramNames.length != paramValues.length) {
            return;
        } else {
            for (int i = 0; i < paramNames.length; i++) {
                addParamAndValue(paramNames[i], paramValues[i]);
            }
        }
    }

    public NavTarget exOp() {
        final NavTarget target = new NavTarget(getParams());
        target.getParams().remove(Navigable.OP_KEY);
        return target;
    }

    public String getParamValue(String paramName) {
        return getParams().get(paramName);
    }

    public String getParamValue(NodeType nodeType) {
        return getParams().get(nodeType.getId());
    }

    public boolean contains(String paramValue) {
        boolean result = false;
        if (params.values().contains(paramValue)) {
            result = true;
        }
        return result;
    }

    private Map<String, String> getParams() {
        return params;
    }

    public Operation getOp() {
        Operation result = null;
        String opCode;
        String targetOpCode;
        for (final Operation op: Operation.values()) {
            opCode = op.getCode();
            targetOpCode = getParamValue(Navigable.OP_KEY);
            if (targetOpCode != null & opCode.equals(targetOpCode)) {
                result = op;
                break;
            }
        }
        return result;
    }

    public String toUri() {
        final StringBuffer uri = new StringBuffer();
        String result = "";
        String paramName, paramValue = null;

        for (final Map.Entry<String, String> e: getParams().entrySet()) {
            // TODO escape value?
            paramName = e.getKey();
            paramValue = e.getValue();
            if (!paramName.equals(Navigable.OP_KEY)) {
                uri.append(paramName).append("=").append(paramValue).append("&");
            }
        }
        // strip last &
        final String raw = uri.toString();
        if (raw.endsWith("&")) {
            result = raw.substring(0, raw.length() - 1);
        }
        return result;
    }

}
