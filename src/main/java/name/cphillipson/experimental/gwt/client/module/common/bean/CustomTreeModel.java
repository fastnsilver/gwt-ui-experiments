package name.cphillipson.experimental.gwt.client.module.common.bean;

import java.util.Collection;
import java.util.LinkedList;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import name.cphillipson.experimental.gwt.client.module.common.service.TokenService;
import name.cphillipson.experimental.gwt.client.module.common.widget.popup.NavNodeMenu;
import name.cphillipson.experimental.gwt.client.util.NavigableUtil;
import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

public class CustomTreeModel implements TreeViewModel {

    private final NavigationInfo info;
    private final NavigableUtil util;
    private final TokenService tokenService;

    /**
     * A selection model shared across all nodes in the tree.
     */
    private final SingleSelectionModel<NavNode> selectionModel = new SingleSelectionModel<NavNode>();

    public CustomTreeModel(NavigationInfo info, TokenService tokenService) {
        this.info = info;
        util = new NavigableUtil(info);
        this.tokenService = tokenService;
    }

    @Override
    public <T> NodeInfo<?> getNodeInfo(T value) {
        DefaultNodeInfo<NavNode> result = null;
        if (value == null) {
            // LEVEL 0.
            // We passed null as the root value. Return the immediate descendants.
            result = new DefaultNodeInfo<NavNode>(getDataProvider(info.allRootNodes()), getCell(), selectionModel, null);

        } else if (value instanceof NavNode) {
            // all other levels
            // We pass a node, return its immediate descendants.

            // select node if URL contains params in node's target or one of node's option's target
            final NavNode currNode = (NavNode) value;
            if (util.isSelected(currNode)) {
                selectionModel.setSelected(currNode, !selectionModel.isSelected(currNode));
            }
            result = new DefaultNodeInfo<NavNode>(getDataProvider(currNode), getCell(), selectionModel, null);
        }
        return result;
    }

    @Override
    public boolean isLeaf(Object value) {
        boolean result = true;
        if (value == null) {
            result = false;  // assumes all root nodes have children
        } else if (value instanceof NavNode) {
            final NavNode currentNode = (NavNode) value;
            if (currentNode.hasChildren()) {
                result = false;
            }
        }
        return result;
    }

    // Create a data provider for root nodes
    private ListDataProvider<NavNode> getDataProvider(Collection<NavNode> rootNodes) {
        return new ListDataProvider<NavNode>(new LinkedList(rootNodes));
    }

    // Create a data provider that contains the immediate descendants.
    private ListDataProvider<NavNode> getDataProvider(NavNode node) {
        return new ListDataProvider<NavNode>(util.createHeadedChildren(node.getChildren(), 1));
    }

    // Create a cell to display a descendant.
    private Cell<NavNode> getCell() {
        return new TreeCell();
    }



    class TreeCell extends AbstractCell<NavNode> {

        public TreeCell() {
            super("click", "keydown");
        }

        @Override
        public void onBrowserEvent(Context context, Element parent, NavNode currNode,
                NativeEvent event, ValueUpdater<NavNode> valueUpdater) {
            // Check that the value is not null.
            if (currNode == null) {
                return;
            }

            if (currNode.hasOptions()) { // add pop-up menu to this node if it has options
                final NavNodeMenu optionsPopup = new NavNodeMenu(util, tokenService);
                optionsPopup.setInput(currNode);

                // Reposition the popup relative to node
                final int left = parent.getAbsoluteLeft() + 25;
                final int top = parent.getAbsoluteTop();

                optionsPopup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
                    @Override
                    public void setPosition(int offsetWidth, int offsetHeight) {
                        optionsPopup.setPopupPosition(left, top);
                    }
                });
            }

            super.onBrowserEvent(context, parent, currNode, event, valueUpdater);
        }

        @Override
        public void render(Context context, NavNode value, SafeHtmlBuilder sb) {
            if (value != null) {
                sb.appendEscaped(util.createNodeName(value));
            }
        }
    }

}
