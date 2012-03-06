package name.cphillipson.experimental.gwt.shared.bean;

import java.util.HashMap;
import java.util.Map;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.shared.i18n.I18NConstants;

/**
 * Top-level navigational perspectives for the market user interface
 * @author cphillipson
 *
 */
public enum NavPerspective {

    MARKET(I18NConstants.MARKET, 0),
    RESERVE_ZONE(I18NConstants.RESERVE_ZONE, 1),
    SUPPLY(I18NConstants.SUPPLY, 2),
    DEMAND(I18NConstants.DEMAND, 3),
    VIRTUAL(I18NConstants.VIRTUAL, 4),
    ADMINSTRATION(I18NConstants.ADMINISTRATION, 5),
    TRANSACTION_LOG(I18NConstants.TRANSACTION_LOG, 6),
    NOTIFICATONS(I18NConstants.NOTIFICATIONS, 7);

    private String code;
    private int order;

    private NavPerspective(String code, int order) {
        this.code = code;
        this.order = order;
    }

    public String getCode() {
        return code;
    }

    public int getOrder() {
        return order;
    }

    public static NavPerspective getDefault() {
        return MARKET;
    }

    public static NavTarget toNavTarget(NavPerspective perspective) {
        NavTarget target = null;
        if (perspective != null) {
            final Map<String, String> params = new HashMap<String, String>();
            params.put(NodeType.PERSPECTIVE.getId(), perspective.name());
            target = new NavTarget(Operation.getDefault().getCode(), params);
        }
        return target;
    }

    public static NavPerspective fromNavTarget(NavTarget target) {
        NavPerspective perspective = null;
        if (target != null) {
            perspective = Enum.valueOf(NavPerspective.class, target.getParamValue(NodeType.PERSPECTIVE));
        }
        return perspective;
    }
}
