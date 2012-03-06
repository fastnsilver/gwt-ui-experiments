package name.cphillipson.experimental.gwt.client.module.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public abstract class PriceMwPairDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 0, message = "{common.mw.min}")
    @Max(value = Integer.MAX_VALUE, message = "{common.mw.max}")
    private BigDecimal mw;

    public void setMw(BigDecimal mw) {
        this.mw = mw;
    }

    public BigDecimal getMw() {
        return mw;
    }

    public abstract void setPrice(BigDecimal price);

    public abstract BigDecimal getPrice();

}
