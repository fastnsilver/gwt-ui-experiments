package name.cphillipson.experimental.gwt.client.module.energy.rpc;

import java.util.List;

import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferDTO;
import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferIdDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The asynchronous counterpart to <code>EnergyOfferService</code>.
 * @author cphillipson
 */
public interface EnergyOfferServiceAsync {

    void getEnergyOffers(EnergyOfferIdDTO criteria, AsyncCallback<List<EnergyOfferDTO>> callback);
}
