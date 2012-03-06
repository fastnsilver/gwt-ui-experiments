package name.cphillipson.experimental.gwt.server.nav;

import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.MONITOR;
import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.QUERY;

import name.cphillipson.experimental.gwt.shared.bean.NodeType;

public class TransactionLogNavigationConstructor extends AbstractNavigationConstructor {

    @Override
    public void seedNavInfo() {
        builder.buildNavNode(null, localize(QUERY), NodeType.QUERY_TRANSACTIONS);
        builder.buildNavNode(null, localize(MONITOR), NodeType.MONITOR_TRANSACTIONS);
    }
}

