package name.cphillipson.experimental.gwt.client.module.common.dto;

import java.io.Serializable;


public class MarketDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resourceName;
    private String dateTime;
    private String marketType;


    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }
}
