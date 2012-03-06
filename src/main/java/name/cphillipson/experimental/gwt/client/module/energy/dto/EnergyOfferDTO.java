package name.cphillipson.experimental.gwt.client.module.energy.dto;

import java.util.ArrayList;
import java.util.List;

import name.cphillipson.experimental.gwt.client.module.common.dto.MarketDTO;
import name.cphillipson.experimental.gwt.client.module.common.dto.OfferPriceMwPairDTO;


public class EnergyOfferDTO extends MarketDTO {

    private static final long serialVersionUID = 1L;

    private boolean slope;
    private List<OfferPriceMwPairDTO> offerPriceCurve = new ArrayList<OfferPriceMwPairDTO>();

    public boolean isSlope() {
        return slope;
    }

    public void setSlope(boolean slope) {
        this.slope = slope;
    }

    public List<OfferPriceMwPairDTO> getOfferPriceCurve() {
        return offerPriceCurve;
    }

    public void setOfferPriceCurve(List<OfferPriceMwPairDTO> offerPriceCurve) {
        this.offerPriceCurve = offerPriceCurve;
    }
}
