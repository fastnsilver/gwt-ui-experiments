package name.cphillipson.experimental.gwt.server.nav;



import java.util.Map;
import java.util.Set;

import name.cphillipson.experimental.gwt.server.util.RandomUtil;
import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;
import name.cphillipson.experimental.gwt.shared.bean.Operation;
import name.cphillipson.experimental.gwt.shared.i18n.I18NConstants;

public class VirtualNavigationConstructor extends AbstractNavigationConstructor {

    // XXX Are these plural since we're looking at set of offers?
    enum Cleared {

        BIDS(I18NConstants.BIDS),
        OFFERS(I18NConstants.OFFERS);

        private String code;

        private Cleared(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    // XXX are these going to work out?
    enum VirtualType {

        OFFER(I18NConstants.OFFER, NodeType.VIRTUAL_OFFER),
        BID(I18NConstants.BID, NodeType.VIRTUAL_BID);

        private String code;
        private NodeType nodeType;

        private VirtualType(String code, NodeType nodeType) {
            this.code = code;
            this.nodeType = nodeType;
        }

        public String getCode() {
            return code;
        }

        public NodeType getNodeType() {
            return nodeType;
        }
    }

    @Override
    public void seedNavInfo() {
        final Set<NavNode> days = createDays();
        for (final NavNode day: days) {
            seedLocations(day);
            seedClearedOffers(day);
        }
    }

    protected void seedLocations(NavNode day) {
        final NavNode locations = builder.buildNavNode(day, localize(I18NConstants.LOCATIONS), NodeType.LOCATIONS);
        final String locationName = null;
        for (int i = 0; i < LOCATION_NUMBER_THRESHOLD; i++) {
            // FIXME get locations from an as yet to be plumbed service
            //locationName = dataSource.getLocation(i);
            seedLocation(locations, locationName);
        }
    }

    protected void seedLocation(NavNode parent, String name) {
        // if we have a bid/offer for RTBM and DAMKT or not, mocking what we would otherwise "learn" from real-data
        final boolean existing = RandomUtil.createBoolean();

        final NavNode location = builder.buildNavNode(parent, name, NodeType.LOCATION);

        // How do we spin through ResourceType#values() setting options below? Additional context may need to be provided to NavOption for appropriate targeting.
        /*
        // FIXME logic will need to be adjusted here for a real use-case... we must check for existence of either-or not both
        for (final MarketChoice choice: MarketChoice.values()) {
            if (existing) {
                builder.buildNavOption(location, localize(choice.getCode()), choice.getMarketType().name(), NodeType.MARKET_TYPE, Operation.UPLOAD);
            } else {
                builder.buildNavOption(location, localize(choice.getCode()), choice.getMarketType().name(), NodeType.MARKET_TYPE, Operation.ADD);
                builder.buildNavOption(location, localize(choice.getCode()), choice.getMarketType().name(), NodeType.MARKET_TYPE, Operation.UPLOAD);
            }
        }
         */

        NavNode virtualType;
        for (final VirtualType type: VirtualType.values()) {
            virtualType = builder.buildNavNode(location, localize(type.getCode()), type.getNodeType().getId(), type.getNodeType());

            for (final MarketChoice choice: MarketChoice.values()) {
                if (existing) {
                    seedExistingLocationOptions(virtualType, type, choice);
                }
            }
        }
    }

    private void seedExistingLocationOptions(NavNode parent, VirtualType type, MarketChoice choice) {
        final String code = choice.getCode();
        if (!choice.equals(MarketChoice.BOTH)) {
            final Map<String, String> params = MarketChoice.getMarketParams(choice);
            builder.buildNavOption(parent, localize(code), type.getCode(), type.getNodeType(), Operation.VIEW, params);
            builder.buildNavOption(parent, localize(code), type.getCode(), type.getNodeType(), Operation.EDIT, params);
            builder.buildNavOption(parent, localize(code), type.getCode(), type.getNodeType(), Operation.COPY, params);
            builder.buildNavOption(parent, localize(code), type.getCode(), type.getNodeType(), Operation.DELETE, params);
            builder.buildNavOption(parent, localize(code), type.getCode(), type.getNodeType(), Operation.DOWNLOAD, params);
        }
    }

    private void seedClearedOffers(NavNode day) {
        final NavNode cleared = builder.buildNavNode(day, localize(I18NConstants.CLEARED), null, NodeType.CLEARED);
        NavNode child;
        for (final Cleared bo: Cleared.values()) {
            child = builder.buildNavNode(cleared, localize(bo.getCode()), null, NodeType.CLEARED);

            // XXX do we want RT, DA, and both?
            for (final MarketChoice choice: MarketChoice.values()) {
                if (!choice.equals(MarketChoice.BOTH)) {
                    builder.buildNavOption(child, localize(choice.getCode()), choice.getMarketType().name(), NodeType.MARKET_TYPE, Operation.VIEW);
                    builder.buildNavOption(child, localize(choice.getCode()), choice.getMarketType().name(), NodeType.MARKET_TYPE, Operation.DOWNLOAD);
                }
            }
        }
    }

}