package name.cphillipson.experimental.gwt.server.controller;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import name.cphillipson.experimental.gwt.client.module.main.rpc.NavigationService;
import name.cphillipson.experimental.gwt.server.nav.NavigationFactory;
import name.cphillipson.experimental.gwt.shared.Endpoints;
import name.cphillipson.experimental.gwt.shared.bean.NavPerspective;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

@Controller
@RequestMapping(Endpoints.SERVER_GWT_PREFIX + Endpoints.GET_NAVIGATION)
public class DefaultNavigationService extends BaseRemoteService implements NavigationService {

    @Inject
    private NavigationFactory factory;

    @Override
    public NavigationInfo getNavInfo(NavPerspective perspective) {
        return factory.getInstance(perspective).constructNavInfo();
    }

}
