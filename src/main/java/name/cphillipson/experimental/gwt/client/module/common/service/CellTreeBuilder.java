package name.cphillipson.experimental.gwt.client.module.common.service;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.view.client.TreeViewModel;
import com.mvp4g.client.event.BaseEventHandler;
import name.cphillipson.experimental.gwt.client.module.common.bean.CustomTreeModel;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

/**
 * Builds a GWT {@link CellTree} based on supplied {@link CommonTokenService} and {@link NavNode}.
 * @author cphillipson
 *
 */
public class CellTreeBuilder implements NavBuilder<CellTree> {

    // before "Show more" kicks in
    private static final int CHILD_NODE_THRESHOLD = 50;

    private BaseEventHandler<MainEventBus> presenter;

    public CellTreeBuilder(BaseEventHandler<MainEventBus> presenter) {
        this.presenter = presenter;
    }

    @Override
    public CellTree build(NavigationInfo info) {
        final TokenService tokenService = new CommonTokenService<MainEventBus>(presenter, info);
        final TreeViewModel model = new CustomTreeModel(info, tokenService);
        final CellTree tree = new CellTree(model, null);
        tree.setDefaultNodeSize(CHILD_NODE_THRESHOLD);
        tree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
        return tree;
    }

}
