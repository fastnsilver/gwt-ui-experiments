package name.cphillipson.experimental.gwt.server.nav;

import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.SETTINGS;
import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.VERSION;

import name.cphillipson.experimental.gwt.shared.bean.NodeType;


public class AdministrationNavigationConstructor extends AbstractNavigationConstructor {

    @Override
    public void seedNavInfo() {
        builder.buildNavNode(null, localize(VERSION), NodeType.VERSION);
        builder.buildNavNode(null, localize(SETTINGS), NodeType.SETTINGS);
    }

}
