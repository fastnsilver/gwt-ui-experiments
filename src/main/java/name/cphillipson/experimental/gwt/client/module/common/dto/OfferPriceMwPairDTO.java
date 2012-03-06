package name.cphillipson.experimental.gwt.client.module.common.dto;

import java.math.BigDecimal;

public class OfferPriceMwPairDTO extends PriceMwPairDTO {

    // GWT does not like custom validators tied to DAOs
    // Consider constrained set of JSR-303 annotations for use in DTOs
    //@Price(minLimitKey="MINVIRTUALOFFERPRICELIMIT", maxLimitKey="MAXVIRTUALOFFERPRICELIMIT")
    private BigDecimal price;

    public OfferPriceMwPairDTO() {
        super();
    }

    public OfferPriceMwPairDTO(BigDecimal mw, BigDecimal price) {
        setMw(mw);
        this.price = price;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

}
