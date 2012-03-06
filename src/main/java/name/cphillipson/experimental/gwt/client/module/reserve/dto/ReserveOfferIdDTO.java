package name.cphillipson.experimental.gwt.client.module.reserve.dto;

import java.io.Serializable;

import name.cphillipson.experimental.gwt.client.module.common.dto.MarketDTO;

public class ReserveOfferIdDTO extends MarketDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resourceName;
    private String date;
    private String marketType;
    private String productType;

    public ReserveOfferIdDTO() {
        super();
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }

    @Override
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String getMarketType() {
        return marketType;
    }

    @Override
    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

}
