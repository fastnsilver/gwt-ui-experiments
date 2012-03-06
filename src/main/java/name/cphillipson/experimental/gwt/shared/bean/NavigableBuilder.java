package name.cphillipson.experimental.gwt.shared.bean;

import java.util.Map;

import name.cphillipson.experimental.gwt.client.util.ClientSafeUUIDGenerator;

/**
 * Shared utility for building <code>NavNode</code>s and <code>NavOption</code>s.
 * @author cphillipson
 *
 */
public class NavigableBuilder {

    private final NavigationInfo info;

    public NavigableBuilder(NavigationInfo info) {
        this.info = info;
    }

    public NavOption buildNavOption(Navigable parent, String name, String targetName, NodeType nodeType, Operation operation) {
        return buildNavOption(parent, name, targetName, nodeType, operation, null);
    }

    public NavOption buildNavOption(Navigable parent, String name, String targetName, NodeType nodeType, Operation operation, Map<String, String> params) {
        final String type = nodeType == null ? "" : nodeType.getId();
        final NavOption option = new NavOption(name, type, params);
        option.setTargetName(targetName);
        option.setId(ClientSafeUUIDGenerator.uuid());
        if (parent != null) {
            if (parent instanceof NavNode) {
                final NavNode papa = (NavNode) parent;
                papa.addOption(option);
                option.setParent(papa.getId());
            } else if (parent instanceof NavOption) {
                final NavOption papa = (NavOption) parent;
                papa.addChild(option);
                option.setParent(papa.getId());
            }
        }
        if (operation != null) {
            option.setOp(operation.getCode());
        }
        info.addOption(option);
        return option;
    }

    public NavNode buildNavNode(NavNode parent, String name, NodeType nodeType) {
        return buildNavNode(parent, name, false, nodeType);
    }

    public NavNode buildNavNode(NavNode parent, String name, String targetName, NodeType nodeType) {
        return buildNavNode(parent, name, targetName, false, nodeType, null);
    }

    public NavNode buildNavNode(NavNode parent, String name, boolean childrenCounted, NodeType nodeType) {
        return buildNavNode(parent, name, null, childrenCounted, nodeType, null);
    }

    public NavNode buildNavNode(NavNode parent, String name, String targetName, boolean childrenCounted, NodeType nodeType, Operation operation) {
        final String type = nodeType == null ? "" : nodeType.getId();
        final NavNode node = new NavNode(name, childrenCounted, type);
        node.setTargetName(targetName);
        node.setId(ClientSafeUUIDGenerator.uuid());
        if (parent != null) {
            node.setParent(parent.getId());
            parent.addChild(node);
        }
        if (operation != null) {
            node.setOp(operation.getCode());
        }
        info.addNode(node);
        return node;
    }

    public NavNode buildNavNode(NavNode parent, String name, int countOverride, NodeType nodeType) {
        return buildNavNode(parent, name, null, countOverride, nodeType, null);
    }

    public NavNode buildNavNode(NavNode parent, String name, String targetName, int countOverride, NodeType nodeType, Operation operation) {
        final String type = nodeType == null ? "" : nodeType.getId();
        final NavNode node = new NavNode(name, countOverride, type);
        node.setTargetName(targetName);
        node.setId(ClientSafeUUIDGenerator.uuid());
        if (parent != null) {
            node.setParent(parent.getId());
            parent.addChild(node);
        }
        if (operation != null) {
            node.setOp(operation.getCode());
        }
        info.addNode(node);
        return node;
    }
}
