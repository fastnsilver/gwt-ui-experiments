package name.cphillipson.experimental.gwt.shared.bean;

import java.util.Map;


/**
 * <p>A <code>NavNode</code> may have a list of options.
 * Each option is like a NavNode save for the fact that it does not maintain a ref back to its <code>NavNode</code> parent.
 * 
 * @see NavNode
 * @author cphillipson
 *
 */
public class NavOption extends NavCommon {

    private static final long serialVersionUID = 1L;

    private Map<String, String> params;

    public NavOption() {
        // because GWT's serialization rules say I must have a zero-argument constructor
    }

    public NavOption(String name, String type, Map<String, String> params) {
        this.name = name;
        this.type = type;
        this.params = params;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
