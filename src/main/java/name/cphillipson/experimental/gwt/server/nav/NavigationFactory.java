package name.cphillipson.experimental.gwt.server.nav;

import name.cphillipson.experimental.gwt.shared.bean.NavPerspective;

public interface NavigationFactory {
    NavigationConstructor getInstance(NavPerspective perspective);
}
