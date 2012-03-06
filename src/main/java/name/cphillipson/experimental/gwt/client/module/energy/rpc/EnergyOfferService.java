package name.cphillipson.experimental.gwt.client.module.energy.rpc;

import java.util.List;

import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferDTO;
import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferIdDTO;
import name.cphillipson.experimental.gwt.shared.Endpoints;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(Endpoints.CLIENT_GWT_PREFIX + Endpoints.GET_ENERGY_OFFERS)
public interface EnergyOfferService extends RemoteService {

    List<EnergyOfferDTO> getEnergyOffers(EnergyOfferIdDTO criteria) throws IllegalArgumentException;

}
