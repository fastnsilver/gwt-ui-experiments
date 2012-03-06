package name.cphillipson.experimental.gwt.server.nav;

import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.DEMAND_CURVE;
import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.OBLIGATIONS;
import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.REQUIREMENTS;

import java.util.Set;

import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;

public class ReserveZoneNavigationConstructor extends AbstractNavigationConstructor {

    @Override
    public void seedNavInfo() {
        final Set<NavNode> days = createDays();
        for (final NavNode day: days) {
            seedReserveZones(day);
        }
    }

    private void seedReserveZones(NavNode day) {
        NavNode zone = null;
        for (int i = 1; i <= ZONE_NUMBER_THRESHOLD; i++) {
            zone = builder.buildNavNode(day, "Zone " + i, NodeType.RESERVE_ZONE);
            builder.buildNavNode(zone, localize(DEMAND_CURVE), NodeType.DEMAND_CURVE);
            builder.buildNavNode(zone, localize(REQUIREMENTS), NodeType.REQUIREMENTS);
            builder.buildNavNode(zone, localize(OBLIGATIONS), NodeType.OBLIGATIONS);
        }
    }
}