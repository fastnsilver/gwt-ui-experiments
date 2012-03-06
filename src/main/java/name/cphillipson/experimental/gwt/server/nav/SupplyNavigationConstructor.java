package name.cphillipson.experimental.gwt.server.nav;

import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.CLEARED_OFFERS;
import static name.cphillipson.experimental.gwt.shared.i18n.I18NConstants.RESOURCES;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import name.cphillipson.experimental.gwt.client.module.common.dto.ProductType;
import name.cphillipson.experimental.gwt.client.module.common.dto.ReserveProductType;
import name.cphillipson.experimental.gwt.client.module.energy.dto.RampRateTypeType;
import name.cphillipson.experimental.gwt.server.util.RandomUtil;
import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;
import name.cphillipson.experimental.gwt.shared.bean.Operation;
import name.cphillipson.experimental.gwt.shared.i18n.I18NConstants;

public class SupplyNavigationConstructor extends AbstractNavigationConstructor {

    // XXX Are these plural since we're looking at set of offers?
    enum ClearedOffer {

        ALL(I18NConstants.ALL),
        ENERGY(I18NConstants.ENERGY),
        REG(I18NConstants.REGULATION),
        SUPP(I18NConstants.SUPPLEMENTAL),
        SPIN(I18NConstants.SPINNING);

        private String code;

        private ClearedOffer(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    // XXX are these going to work out?
    enum ResourceType {

        PARAMETERS(NodeType.PARAMETERS, null, null),
        ENERGY_OFFER(NodeType.ENERGY_OFFER, NodeType.PRODUCT_TYPE.getId(), ProductType.ENERGY.name()),
        RAMP_RATE_UP(NodeType.RAMP_RATE_UP, NodeType.RAMP_RATE_TYPE.getId(), RampRateTypeType.UP.name()),
        RAMP_RATE_DOWN(NodeType.RAMP_RATE_DOWN, NodeType.RAMP_RATE_TYPE.getId(), RampRateTypeType.DN.name()),
        REGULATION_OFFER_UP(NodeType.REGULATION_OFFER_UP, NodeType.PRODUCT_TYPE.getId(), ReserveProductType.REGUP.name()),
        REGULATION_OFFER_DOWN(NodeType.REGULATION_OFFER_DOWN, NodeType.PRODUCT_TYPE.getId(), ReserveProductType.REGDN.name()),
        SPINNING_OFFER(NodeType.SPINNING_OFFER, NodeType.PRODUCT_TYPE.getId(), ReserveProductType.SPIN.name()),
        SUPPLEMENTAL_OFFER(NodeType.SUPPLEMENTAL_OFFER, NodeType.PRODUCT_TYPE.getId(), ReserveProductType.SUPP.name());

        private String id;
        private NodeType nodeType;
        private Map<String, String> params;

        private ResourceType(NodeType nodeType, String paramKey, String paramValue) {
            params = new HashMap<String, String>();
            this.nodeType = nodeType;
            id = nodeType.getId();
            if (paramValue != null) {
                if (paramKey == null) {
                    params.put(id, paramValue);
                } else {
                    params.put(paramKey, paramValue);
                }
            }
        }

        public String getId() {
            return id;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public NodeType getNodeType() {
            return nodeType;
        }

    }

    @Override
    public void seedNavInfo() {
        final Set<NavNode> days = createDays();
        for (final NavNode day: days) {
            seedResources(day);
            seedClearedOffers(day);
        }
    }

    protected void seedResources(NavNode day) {
        final NavNode resources = builder.buildNavNode(day, localize(RESOURCES), NodeType.RESOURCES);
        final String resourceName = null;
        for (int i = 0; i < RESOURCE_NUMBER_THRESHOLD; i++) {
            // FIXME get resources from an as yet to be plumbed service
            //resourceName = dataSource.getResource(i);
            seedResource(resources, resourceName);
        }
    }

    protected void seedResource(NavNode parent, String name) {
        // if we have a bid/offer for RTBM and DAMKT or not, mocking what we would otherwise "learn" from real-data
        final boolean existing = RandomUtil.createBoolean();

        final NavNode resource = builder.buildNavNode(parent, name, NodeType.RESOURCE);

        // How do we spin through ResourceType#values() setting options below? Additional context may need to be provided to NavOption for appropriate targeting.
        /*
        // FIXME logic will need to be adjusted here for a real use-case... we must check for existence of either-or not both
        for (final MarketChoice choice: MarketChoice.values()) {
            if (existing) {
                builder.buildNavOption(resource, localize(choice.getCode()), choice.getMarketType().name(), NodeType.MARKET_TYPE, Operation.UPLOAD);
            } else {
                builder.buildNavOption(resource, localize(choice.getCode()), choice.getMarketType().name(), NodeType.MARKET_TYPE, Operation.ADD);
                builder.buildNavOption(resource, localize(choice.getCode()), choice.getMarketType().name(), NodeType.MARKET_TYPE, Operation.UPLOAD);
            }
        }
         */

        NavNode resourceType;
        for (final ResourceType type: ResourceType.values()) {
            resourceType = builder.buildNavNode(resource, localize(type.getId()), type.getId(), type.getNodeType());

            for (final MarketChoice choice: MarketChoice.values()) {
                if (existing) {
                    seedExistingResourceOptions(resourceType, type, choice);
                }
            }
        }
    }

    private void seedExistingResourceOptions(NavNode parent, ResourceType type, MarketChoice choice) {
        final String code = choice.getCode();
        if (!choice.equals(MarketChoice.BOTH)) {
            final Map<String, String> params = new HashMap<String, String>();
            params.putAll(MarketChoice.getMarketParams(choice));
            params.putAll(type.getParams());
            builder.buildNavOption(parent, localize(code), null, type.getNodeType(), Operation.VIEW, params);
            builder.buildNavOption(parent, localize(code), null, type.getNodeType(), Operation.EDIT, params);
            builder.buildNavOption(parent, localize(code), null, type.getNodeType(), Operation.COPY, params);
            builder.buildNavOption(parent, localize(code), null, type.getNodeType(), Operation.DELETE, params);
            builder.buildNavOption(parent, localize(code), null, type.getNodeType(), Operation.DOWNLOAD, params);
        }
    }

    private void seedClearedOffers(NavNode day) {
        final NavNode cleared = builder.buildNavNode(day, localize(CLEARED_OFFERS), null, NodeType.CLEARED_OFFERS);
        NavNode child;
        for (final ClearedOffer offer: ClearedOffer.values()) {
            child = builder.buildNavNode(cleared, localize(offer.getCode()), null, NodeType.CLEARED_OFFER);

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