package name.cphillipson.experimental.gwt.server.nav;

import java.util.Set;

import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;
import name.cphillipson.experimental.gwt.shared.bean.Operation;
import name.cphillipson.experimental.gwt.shared.i18n.I18NConstants;

public class MarketNavigationConstructor extends AbstractNavigationConstructor {

    @Override
    public void seedNavInfo() {
        final Set<NavNode> days = createDays();
        for (final NavNode day: days) {
            seedMessages(day);
            seedLocations(day);
            seedDemandCurve(day);
            seedOverrides(day);
            seedDistributionFactors(day);
            seedViolationRelaxationLimits(day);
            // the following nodes have RTBM and DAMKT options
            seedMwSummary(day);
            seedPriceSummary(day);
            seedBindingLimits(day);
            seedTimelineEvents(day);
        }
    }

    private void seedMarketOptions(NavNode node) {
        builder.buildNavOption(node, localize(I18NConstants.REAL_TIME_BALANCING_MARKET_SHORT_FORM), MarketChoice.RTBM.getMarketType().name(), NodeType.MARKET_TYPE, Operation.VIEW);
        builder.buildNavOption(node, localize(I18NConstants.DAY_AHEAD_MARKET_SHORT_FORM), MarketChoice.DAMKT.getMarketType().name(), NodeType.MARKET_TYPE, Operation.VIEW);
        // XXX Do we want option to present both?
    }

    private void seedTimelineEvents(NavNode day) {
        final NavNode timeLineEvents = builder.buildNavNode(day, localize(I18NConstants.TIMELINE_EVENTS), NodeType.TIMELINE_EVENTS);
        seedMarketOptions(timeLineEvents);
    }

    private void seedBindingLimits(NavNode day) {
        final NavNode bindingLimits = builder.buildNavNode(day, localize(I18NConstants.BINDING_LIMITS), NodeType.BINDING_LIMITS);
        seedMarketOptions(bindingLimits);
    }

    private void seedPriceSummary(NavNode day) {
        final NavNode priceSummary = builder.buildNavNode(day, localize(I18NConstants.PRICE_SUMMARY), NodeType.PRICE_SUMMARY);
        seedMarketOptions(priceSummary);
    }

    private void seedMwSummary(NavNode day) {
        final NavNode mwSummary = builder.buildNavNode(day, localize(I18NConstants.MW_SUMMARY), NodeType.MW_SUMMARY);
        seedMarketOptions(mwSummary);
    }

    private void seedViolationRelaxationLimits(NavNode day) {
        builder.buildNavNode(day, localize(I18NConstants.VIOLATION_RELAXATION_LIMITS), "VRL", NodeType.VIOLATION_RELAXATION_LIMITS);
    }

    private void seedOverrides(NavNode day) {
        builder.buildNavNode(day, localize(I18NConstants.OVERRIDES), NodeType.OVERRIDES);
    }

    private void seedDistributionFactors(NavNode day) {
        builder.buildNavNode(day, localize(I18NConstants.DISTRIBUTION_FACTORS), NodeType.DISTRIBUTION_FACTORS);
    }

    private void seedDemandCurve(NavNode day) {
        builder.buildNavNode(day, localize(I18NConstants.DEMAND_CURVE), NodeType.DEMAND_CURVE);
    }

    private void seedLocations(NavNode day) {
        builder.buildNavNode(day, localize(I18NConstants.LOCATIONS), NodeType.LOCATIONS);
    }

    private void seedMessages(NavNode day) {
        builder.buildNavNode(day, localize(I18NConstants.MESSAGES), NodeType.MESSAGES);
    }

}
