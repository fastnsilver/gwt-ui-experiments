package name.cphillipson.experimental.gwt.server.controller.stub;

import name.cphillipson.experimental.gwt.client.module.common.dto.MarketStatusDTO;
import name.cphillipson.experimental.gwt.server.controller.MarketService;

public class StubMarketService implements MarketService {

    @Override
    public MarketStatusDTO getMarketStatus() {
        return MarketStatusDTO.OPEN;  // always open for trading
    }
}
