package name.cphillipson.experimental.gwt.client.module.common.widget.nav;

import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.ui.FlowPanel;
import com.mvp4g.client.event.BaseEventHandler;
import name.cphillipson.experimental.gwt.client.module.common.service.CellTreeBuilder;
import name.cphillipson.experimental.gwt.client.module.common.service.NavBuilder;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

/**
 * Renders a GWT <code>CellTree</code> when supplied an instance of <code>NavigationInfo<code> and the
 * view's presenter (as an MVP4G <code>BaseEventHandler</code>).
 * @author cphillipson
 *
 */
public class NavPanel extends FlowPanel {

    public NavPanel() {
        super();
        setDefaults();
    }

    private void setDefaults() {
        // TODO set defaults, e.g., style
    }

    public void setInput(NavigationInfo info, BaseEventHandler<MainEventBus> presenter) {
        this.clear();
        if (info == null) {
            return;
        }
        final NavBuilder<CellTree> treeBuilder = new CellTreeBuilder(presenter);
        final CellTree tree = treeBuilder.build(info);
        this.add(tree);
    }
}
