package name.cphillipson.experimental.gwt.server.nav;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import name.cphillipson.experimental.gwt.client.module.common.dto.MarketTypeType;
import name.cphillipson.experimental.gwt.server.dao.TestData;
import name.cphillipson.experimental.gwt.server.i18n.MessageHelper;
import name.cphillipson.experimental.gwt.server.util.TimeUtil;
import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NavigableBuilder;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;
import name.cphillipson.experimental.gwt.shared.i18n.I18NConstants;

import org.joda.time.DateTime;

public abstract class AbstractNavigationConstructor implements NavigationConstructor {

    // XXX Q: What do we really want the individual hierarchies to look like?
    // Hierarchies provide necessary data for construction of NavTarget for each node on client-side
    // NavTarget is then used as criteria for get*-oriented data services
    // TODO Arbitrary arrangements like below should be made more concrete per perspective

    protected MessageHelper messageHelper;

    // TINY
    protected static final int OPERATING_DAYS_THRESHOLD = 3;    // should always be less than or equal to 14
    protected static final int LOCATION_NUMBER_THRESHOLD = 30;
    protected static final int RESOURCE_NUMBER_THRESHOLD = 30;  // should always be less than or equal to 250
    protected static final int ZONE_NUMBER_THRESHOLD = 3;

    // SMALL
    /*
    protected static final int OPERATING_DAYS_THRESHOLD = 14;   // should always be less than or equal to 14
    protected static final int LOCATION_NUMBER_THRESHOLD = 100;
    protected static final int RESOURCE_NUMBER_THRESHOLD = 25;  // should always be less than or equal to 250
    protected static final int ZONE_NUMBER_THRESHOLD = 10;
     */

    // BIG
    /*
    protected static final int OPERATING_DAYS_THRESHOLD = 14;     // should always be less than or equal to 14
    protected static final int LOCATION_NUMBER_THRESHOLD = 100;
    protected static final int RESOURCE_NUMBER_THRESHOLD = 100;  // should always be less than or equal to 250
    protected static final int ZONE_NUMBER_THRESHOLD = 20;
     */

    protected NavigationInfo info;
    protected NavigableBuilder builder;

    protected enum MarketChoice {

        BOTH(I18NConstants.BOTH, null),
        DAMKT(I18NConstants.DAY_AHEAD_MARKET_SHORT_FORM, MarketTypeType.DAMKT),
        RTBM(I18NConstants.REAL_TIME_BALANCING_MARKET_SHORT_FORM, MarketTypeType.RTBM);

        private String code;
        private MarketTypeType marketType;

        private MarketChoice(String code, MarketTypeType marketType) {
            this.code = code;
            this.marketType = marketType;
        }

        public String getCode() {
            return code;
        }

        public MarketTypeType getMarketType() {
            return marketType;
        }

        public static Map<String, String> getMarketParams(MarketChoice choice) {
            final Map<String, String> params = new HashMap<String, String>();
            params.put(NodeType.MARKET_TYPE.getId(), choice.getMarketType().name());
            return params;
        }
    }

    @Override
    public void setMessageHelper(MessageHelper messageHelper) {
        this.messageHelper = messageHelper;
    }


    @Override
    public NavigationInfo constructNavInfo() {

        info = new NavigationInfo();
        builder = new NavigableBuilder(info);

        seedNavInfo();

        return info;
    }

    protected String localize(String code) {
        return messageHelper.obtainLocalizedValue(code);
    }

    protected abstract void seedNavInfo();

    protected Set<NavNode> createDays() {
        final Set<NavNode> days = new HashSet<NavNode>();
        // build two weeks worth of operating days, starting from last week
        DateTime now = TestData.SEVEN_DAYS_AGO_AT_MIDNIGHT.toDateTime();
        final DateTime currDay = now;
        for (int i = 1; i <= OPERATING_DAYS_THRESHOLD; i++) {
            days.add(createDay(null, TimeUtil.YEAR_MONTH_DAY_FORMATTER.print(now)));
            now = currDay.plusDays(i);
        }
        return days;
    }

    protected NavNode createDay(NavNode parent, String datum) {
        final NavNode day = builder.buildNavNode(parent, datum, NodeType.OPERATING_DAY);
        return day;
    }

}
