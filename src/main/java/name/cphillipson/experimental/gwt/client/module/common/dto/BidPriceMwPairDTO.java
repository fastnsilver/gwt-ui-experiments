package name.cphillipson.experimental.gwt.client.module.common.dto;

import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class BidPriceMwPairDTO extends PriceMwPairDTO {

    // GWT does not like custom validators tied to DAOs
    // Consider constrained set of JSR-303 annotations for use in DTOs
    //@Price(minLimitKey="MINVIRTUALBIDPRICELIMIT", maxLimitKey="MAXVIRTUALBIDPRICELIMIT")
    @Min(value = 0, message = "{common.price.min}")
    @Max(value = Integer.MAX_VALUE, message = "{common.price.max}")
    private BigDecimal price;

    public BidPriceMwPairDTO() {
    }

    public BidPriceMwPairDTO(BigDecimal price, BigDecimal mw) {
        this.price = price;
        super.setMw(mw);
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
