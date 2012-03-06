package name.cphillipson.experimental.gwt.client.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NavOption;
import name.cphillipson.experimental.gwt.shared.bean.Navigable;
import name.cphillipson.experimental.gwt.shared.bean.NavigableBuilder;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;
import name.cphillipson.experimental.gwt.shared.bean.Operation;

/**
 * Client-side utility for working with <code>Navigable</code>s.
 * @author cphillipson
 *
 */
public class NavigableUtil {

    private static final int CHILDREN_THRESHOLD = 25;

    private final NavigationInfo info;
    private Map<String, Navigable> navigables;
    //private Map<String, NavOption> options;
    private Navigable currentSelection;

    private static Comparator<NavOption> navOpComparator = new Comparator<NavOption>() {

        @Override
        public int compare(NavOption no1, NavOption no2) {
            return StringUtil.nullSafeStringComparator(no1.getName(), no2.getName());
        }

    };

    public NavigableUtil(NavigationInfo info) {
        this.info = info;
    }

    /*
    private Map<String, NavOption> allOptions() {
        if (options == null) {
            options = new HashMap<String, NavOption>();
            NavOption option = null;
            NavigableReference ref = null;
            for (final Map.Entry<NavigableKey, String> rel: info.allOptionToOptionRefRelations().entrySet()) {
                ref = info.allOptionRefs().get(rel.getValue());
                option = new NavOption(ref.getName(), ref.getTargetName(), ref.getType());
                option.setId(rel.getKey().getId());
                option.setParent(rel.getKey().getParent());
                options.put(option.getId(), option);
            }
        }
        return options;
    }
     */

    private Map<String, Navigable> allNavigables() {
        if (navigables == null) {
            navigables = new HashMap<String, Navigable>();
            navigables.putAll(info.allNodes());
            //navigables.putAll(allOptions());
            navigables.putAll(info.allOptions());
        }
        return Collections.unmodifiableMap(navigables);
    }

    public NavNode getNode(String id) {
        return info.allNodes().get(id);
    }

    public Set<NavNode> getNodes(Set<String> set) {
        final Set<NavNode> result = new HashSet<NavNode>();
        for(final String id: set) {
            result.add(getNode(id));
        }
        return result;
    }

    public NavOption getOption(String id) {
        //return allOptions().get(id);
        return info.allOptions().get(id);
    }

    public Set<NavOption> getOptions(NavNode node, Operation op) {
        Set<NavOption> options = null;
        NavOption option;
        NavTarget target;
        for (final String id: node.getOptions()) {
            option = getOption(id);
            target = generateTarget(option);
            if (target.getOp().equals(op)) {
                if (options == null) {
                    // make sure that whatever is returned is ordered by name
                    options = new TreeSet<NavOption>(navOpComparator);
                }
                options.add(option);
            }
        }
        return options;
    }

    public boolean contains(Navigable navigable) {
        boolean result = false;
        if (navigable instanceof NavNode) {
            final NavNode n = (NavNode) navigable;
            if (info.allNodes().values().contains(n)) {
                result = true;
            }
        } else if (navigable instanceof NavOption) {
            final NavOption o = (NavOption) navigable;
            //if (allOptions().values().contains(o)) {
            if (info.allOptions().values().contains(o)) {
                result = true;
            }
        }
        return result;
    }

    public void setCurrentSelection(Navigable currentSelection) {
        this.currentSelection = currentSelection;
    }

    public boolean isSelected(NavNode node) {
        boolean selected = false;
        if (node != null && currentSelection != null) {
            Navigable n = node;
            if (n.equals(currentSelection)) {
                selected = true;
            } else {
                for (final String id: node.getOptions()) {
                    final NavOption option = getOption(id);
                    n = option;
                    if (n.equals(currentSelection)) {
                        selected = true;
                        break;
                    }
                }
            }
        }
        return selected;
    }

    public Navigable getNavigable(String id) {
        return allNavigables().get(id);
    }

    public NavTarget generateTarget(Navigable navigable) {
        NavTarget navTarget = null;
        if (isValid(navigable)) {
            final Map<String, String> params = buildParams(navigable);
            final String operation = navigable.getOp();
            navTarget = new NavTarget(operation, params);
        }
        return navTarget;
    }

    /**
     * Builds params for the <code>NavTarget</code>
     * @param navigable a <code>NavNode</code> or <code>NavOption</code>
     * @return a map of all param names and values of <code>Navigable</code> chain to root
     *          (i.e., iterate up hierarchy until a <code>Navigable</code> whose parent is null is reached)
     */
    private Map<String, String> buildParams(Navigable navigable) {
        final Map<String, String> params = new TreeMap<String, String>();
        params.put(navigable.getType(), navigable.getTargetName());
        if (navigable instanceof NavOption) {
            final NavOption opt = (NavOption) navigable;
            final Map<String, String> optParams = opt.getParams();
            if (optParams != null && !optParams.isEmpty()) {
                params.putAll(optParams);
            }
        }
        buildParams(getNavigable(navigable.getParent()), params);
        return params;
    }

    private void buildParams(Navigable navigable, Map<String, String> params) {
        if (isValid(navigable)) {
            params.put(navigable.getType(), navigable.getTargetName());
            if (navigable.hasParent()) {
                buildParams(getNavigable(navigable.getParent()), params);
            }
        }
    }

    private boolean isValid(Navigable navigable) {
        boolean valid = false;
        if (navigable!= null && navigable.getType() != null && navigable.getTargetName() != null) {
            valid = true;
        }
        return valid;
    }

    private boolean areChildrenAlreadyHeaded(Set<NavNode> children) {
        boolean result = false;
        int childCount = 0;
        for (final NavNode node: children) {
            if(node.isHeaded()) {
                childCount++;
            }
        }
        if (childCount == children.size()) {
            result = true;
        }
        return result;
    }

    public List<NavNode> createHeadedChildren(Set<String> idSet, int headingCount) {
        List<NavNode> result = null;
        final NavigableBuilder builder = new NavigableBuilder(info);
        final Set<NavNode> children = getNodes(idSet);
        if (areChildrenAlreadyHeaded(children)) {
            result = new ArrayList(children);
        } else {
            if (headingCount > 0 && children.size() > CHILDREN_THRESHOLD) {
                result = new ArrayList<NavNode>();

                String headerName, nodeName = null;
                NavNode header = null;
                final Set<NavNode> headers = new TreeSet<NavNode>();  // sorts headings alphabetically

                // construct artificial headings
                for (final NavNode node: children) {
                    headerName = node.getName().substring(0, headingCount).toUpperCase();
                    // all children have the same parent
                    header = builder.buildNavNode(getNode(children.iterator().next().getParent()), headerName, true, NodeType.ALPHA);
                    headers.add(header);
                    node.setHeaded(true);
                }
                result.addAll(headers);

                for (final NavNode h: result) {
                    headerName = h.getName().substring(0, headingCount);
                    for (final NavNode child: children) {
                        nodeName = child.getName().substring(0, headingCount);
                        if (nodeName.equalsIgnoreCase(headerName)) {
                            h.addChild(child);
                        }
                    }
                }
            } else {
                result = new ArrayList(children);
            }
        }
        Collections.sort(result);
        return result;
    }

    public String createNodeName(NavNode node) {
        final StringBuffer result = new StringBuffer();
        result.append(node.getName());
        if (node.areChildrenCounted() || node.getCountOverride() > 0) {
            result.append(" (");
            if (node.areChildrenCounted()) {
                result.append(node.getChildCount());
            } else if (node.getCountOverride() > 0) {
                result.append(node.getCountOverride());
            }
            result.append(")");
        }
        return result.toString();
    }

}
