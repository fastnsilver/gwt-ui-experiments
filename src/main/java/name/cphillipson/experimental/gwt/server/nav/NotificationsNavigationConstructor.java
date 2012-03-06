package name.cphillipson.experimental.gwt.server.nav;

import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.CURRENT;
import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.MONITOR;
import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.URL_MANAGER;

import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;

public class NotificationsNavigationConstructor extends AbstractNavigationConstructor {

    @Override
    public void seedNavInfo() {
        builder.buildNavNode(null, localize(CURRENT), NodeType.CURRENT_NOTIFICATIONS);
        final NavNode monitor = builder.buildNavNode(null, localize(MONITOR), NodeType.MONITOR_NOTIFICATIONS);
        builder.buildNavNode(monitor, localize(URL_MANAGER), NodeType.URL_MANAGER_FOR_NOTIFICATIONS);
    }

}