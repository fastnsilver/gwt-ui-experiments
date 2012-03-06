package name.cphillipson.experimental.gwt.client.module.reserve.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import name.cphillipson.experimental.gwt.client.module.common.dto.MarketDTO;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class ReserveOfferDTO extends MarketDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull @Digits(integer=4, fraction=2)
    private BigDecimal price;
    @NotNull @Digits(integer=4, fraction=1)
    private BigDecimal fixedMW;

    private String dispatchStatus;
    private String productType;

    public ReserveOfferDTO() {
        super();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFixedMW() {
        return fixedMW;
    }

    public void setFixedMW(BigDecimal fixedMW) {
        this.fixedMW = fixedMW;
    }

    public String getDispatchStatus() {
        return dispatchStatus;
    }

    public void setDispatchStatus(String dispatchStatus) {
        this.dispatchStatus = dispatchStatus;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }


}
