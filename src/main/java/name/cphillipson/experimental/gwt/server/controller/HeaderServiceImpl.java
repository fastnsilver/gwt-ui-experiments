package name.cphillipson.experimental.gwt.server.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import name.cphillipson.experimental.gwt.client.module.main.rpc.HeaderService;
import name.cphillipson.experimental.gwt.server.i18n.MessageHelper;
import name.cphillipson.experimental.gwt.server.util.AppUtil;
import name.cphillipson.experimental.gwt.shared.Endpoints;
import name.cphillipson.experimental.gwt.shared.bean.BannerInfo;
import name.cphillipson.experimental.gwt.shared.bean.NavPerspective;
import name.cphillipson.experimental.gwt.shared.bean.NavigableBuilder;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;
import name.cphillipson.experimental.gwt.shared.bean.Operation;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(Endpoints.SERVER_GWT_PREFIX + Endpoints.GET_HEADER)
public class HeaderServiceImpl extends BaseRemoteService implements HeaderService {

    @Inject
    private Environment env;

    @Inject
    private MessageHelper i18nHelper;

    // keeps perspectives ordered by {@link NavPerspective#getOrder()}
    private Comparator<NavPerspective> perspectiveComparator = new Comparator<NavPerspective>() {

        @Override
        public int compare(NavPerspective np1, NavPerspective np2) {
            int comparison = -1;
            if (np1 == np2 || np1.getOrder() == np2.getOrder()) {
                comparison = 0;
            } else if (np1.getOrder() > np2.getOrder()){
                comparison = 1;
            }
            return comparison;
        }

    };

    private String obtainApplicationServerHostName() {
        return AppUtil.getHostName();
    }

    private String obtainDatabaseHostName() {
        final String ipAddress = env.getProperty("db.ipaddress");
        return AppUtil.getHostName(ipAddress);
    }

    // TODO plumb security and ask for the user via the RequestHelper
    // A custom argument resolver will place a User object in each HttpServletRequest to be referenced by key "account"
    private String obtainCurrentUser() {
        return "Joe User";
    }

    @Override
    public BannerInfo getBannerInfo() {
        final BannerInfo info = new BannerInfo();
        info.setDatabase(obtainDatabaseHostName());
        info.setHostname(obtainApplicationServerHostName());
        info.setCurrentUser(obtainCurrentUser());
        info.setVersion(env.getProperty("app.version"));
        info.setAppName(env.getProperty("app.name").toUpperCase());
        return info;
    }

    @Override
    // TODO apply Securable to restrict options to a user based on role
    public NavigationInfo getTopNavInfo() {
        final NavigationInfo info = new NavigationInfo();
        final NavigableBuilder builder = new NavigableBuilder(info);

        final Set<NavPerspective> perspectives = new TreeSet<NavPerspective>(perspectiveComparator);
        perspectives.addAll(Arrays.asList(NavPerspective.values()));

        for (final NavPerspective np: perspectives) {
            builder.buildNavOption(null, i18nHelper.obtainLocalizedValue(np.getCode()), np.name(), NodeType.PERSPECTIVE, Operation.VIEW);
        }

        return info;
    }

}
