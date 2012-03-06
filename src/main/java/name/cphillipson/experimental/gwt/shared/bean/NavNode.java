package name.cphillipson.experimental.gwt.shared.bean;

import java.util.HashSet;
import java.util.Set;

import name.cphillipson.experimental.gwt.client.module.main.view.impl.CellTreeNavView;

/**
 * <p>Meta-model for navigation purposes.  Each node maintains a name, target, type, and a reference to its children.
 * The target is typically a token of URL parameters that ideally may be used by GWT's <code>Hyperlink</code>, e.g.,</p>
 * <blockquote><code>Hyperlink h = new Hyperlink(n.getName(), token);</code></blockquote>
 * <p>where <code>n</code> is a <code>NavNode</code> and <code>token</code> is constructed to invoke an <code>@Event</code>
 * annotated method on an implementation of MVP4G's <code>EventBus</code> using <code>n</code>'s target,
 * i.e., <code>n.getTarget()</code> taking into consideration <code>n.getType()</code>.</p>
 * <p>In addition, each node...<br/>
 * <ul><li>may have zero or more options, where each <code>NavOption</code> has a name and target.</li>
 * <li>may be shown with a count, where a count is either the number immediate descendants or an override.</li></ul></p>
 * 
 * @see CellTreeNavView
 * @author cphillipson
 *
 */
public class NavNode extends NavCommon implements Comparable<NavNode> {

    private static final long serialVersionUID = 1L;

    private Set<String> options;
    private boolean childrenCounted;
    private int countOverride = -1;
    private boolean headed;

    public NavNode() {
        // because GWT's serialization rules say I must have a zero-argument constructor
    }

    public NavNode(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public NavNode(String name, boolean childrenCounted, String type) {
        this(name, type);
        this.childrenCounted = childrenCounted;
    }

    public NavNode(String name, int countOverride, String type) {
        this(name, type);
        this.countOverride = countOverride;
    }

    // note: only counts the number of immediate descendants
    public int getChildCount() {
        return getChildren().size();
    }

    public Set<String> getOptions() {
        if (options == null) {
            options = new HashSet<String>();
        }
        return options;
    }

    public void addOption(NavOption option) {
        getOptions().add(option.getId());
    }

    public boolean hasOptions() {
        boolean result = true;
        if (getOptions().isEmpty()) {
            result = false;
        }
        return result;
    }

    public boolean areChildrenCounted() {
        return childrenCounted;
    }

    public void setChildrenCounted(boolean childrenCounted) {
        this.childrenCounted = childrenCounted;
    }

    public int getCountOverride() {
        return countOverride;
    }

    public void setCountOverride(int countOverride) {
        this.countOverride = countOverride;
    }

    public boolean isHeaded() {
        return headed;
    }

    public void setHeaded(boolean headed) {
        this.headed = headed;
    }

    @Override
    public int compareTo(NavNode node) {
        final int comparison = this.getName().compareTo(node.getName());
        return comparison;
    }
}
