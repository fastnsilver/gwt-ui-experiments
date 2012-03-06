package name.cphillipson.experimental.gwt.shared.bean;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;


/**
 * Payload for navigation concerns.  Services populate and Views consume this data.
 * @author cphillipson
 *
 */
public class NavigationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    // note: these members must be non-final in order for GWT to serialize appropriately
    private Map<String, NavNode> nodes = new LinkedHashMap<String, NavNode>();
    //private Map<String, NavigableReference> optionRefs = new HashMap<String, NavigableReference>();
    private Map<String, NavOption> options = new HashMap<String, NavOption>();
    private Set<NavNode> rootNodes = new LinkedHashSet<NavNode>();
    private Set<NavOption> rootOptions = new LinkedHashSet<NavOption>();
    //private Map<NavigableKey, String> optionToOptionRefRelations = new HashMap<NavigableKey, String>();

    public Map<String, NavNode> allNodes() {
        return Collections.unmodifiableMap(nodes);
    }

    public Map<String, NavOption> allOptions()  {
        return Collections.unmodifiableMap(options);
    }

    /*
    public Map<String, NavigableReference> allOptionRefs() {
        return Collections.unmodifiableMap(optionRefs);
    }

    public Map<NavigableKey, String> allOptionToOptionRefRelations() {
        return Collections.unmodifiableMap(optionToOptionRefRelations);
    }
     */

    public Set<NavNode> allRootNodes() {
        return Collections.unmodifiableSet(rootNodes);
    }

    public Set<NavOption> allRootOptions() {
        return Collections.unmodifiableSet(rootOptions);
    }

    void addNode(NavNode node) {
        nodes.put(node.getId(), node);
        if (node.getParent() == null) {
            rootNodes.add(node);
        }
    }

    void addOption(NavOption option) {
        options.put(option.getId(), option);
        if (option.getParent() == null) {
            rootOptions.add(option);
        }
    }

    /*
    void addOption(NavOption option) {
        final NavigableReference ref = new NavigableReference(option);
        if (!optionRefs.values().contains(ref)) {
            optionRefs.put(ref.getId(), ref);
            optionToOptionRefRelations.put(new NavigableKey(option), ref.getId());
        }
        if (option.getParent() == null) {
            rootOptions.add(option);
        }
    }
     */

    public void clear() {
        nodes.clear();
        options.clear();
        //optionRefs.clear();
        //optionToOptionRefRelations.clear();
        rootNodes.clear();
        rootOptions.clear();
    }

}
