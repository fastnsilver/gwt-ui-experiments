package name.cphillipson.experimental.gwt.client.module.common.service;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.util.NavTargetUtil;
import name.cphillipson.experimental.gwt.client.util.NavigableUtil;
import name.cphillipson.experimental.gwt.shared.bean.Navigable;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Hyperlink;
import com.mvp4g.client.event.BaseEventHandler;

/**
 * All navigation destinations are defined here.  Used to generate a GWT {@link Hyperlink}.
 * 
 * @param <E> an event bus interface which is or extends {@link MainEventBus}
 * @author cphillipson
 *
 */
public class CommonTokenService<E extends MainEventBus> implements TokenService {

    protected final BaseEventHandler<E> handler;
    private final NavigationInfo info;
    private final NavigableUtil util;

    public CommonTokenService(BaseEventHandler<E> handler, NavigationInfo info) {
        this.handler = handler;
        this.info = info;
        util = new NavigableUtil(info);
    }

    @Override
    public String getToken(Navigable navigable) {
        String token = "";
        boolean targeted = true;
        final NavTarget target = util.generateTarget(navigable);
        GWT.log("Target for navigable = " + navigable.getName() + " is " + target.toUri());
        final String type = navigable.getType();
        if (isValid(target)) {  // validate target
            // XXX This is awful, but we cannot employ Reflection in client-side code to instantiate and invoke a method based on a lookup
            if (type.equals(NodeType.REGULATION_OFFER_UP.getId()) && NavTargetUtil.toView(target)) {
                token = handler.getTokenGenerator().viewReserveOffer(target);
            } else if (type.equals(NodeType.REGULATION_OFFER_UP.getId()) && NavTargetUtil.toEdit(target)) {
                token = handler.getTokenGenerator().editReserveOffer(target);
            } else if (type.equals(NodeType.ENERGY_OFFER.getId()) && NavTargetUtil.toView(target)) {
                token = handler.getTokenGenerator().viewEnergyOffer(target);
            } else if (type.equals(NodeType.ENERGY_OFFER.getId()) && NavTargetUtil.toEdit(target)) {
                token = handler.getTokenGenerator().editEnergyOffer(target);
            } else if (type.equals(NodeType.PERSPECTIVE.getId()) && NavTargetUtil.toView(target)) {
                token = handler.getTokenGenerator().populateLeftNav(target);
            } else {
                targeted = false;
            }
            if (targeted) {
                util.setCurrentSelection(navigable);
            }

        }
        return token;
    }

    private boolean isValid(NavTarget target) {
        boolean result = false;
        if (target != null && !target.toUri().isEmpty()) {
            result = true;
        }
        return result;
    }

}
