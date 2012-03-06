package name.cphillipson.experimental.gwt.client.module.reserve.rpc;

import java.util.List;

import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferDTO;
import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferIdDTO;
import name.cphillipson.experimental.gwt.shared.Endpoints;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath(Endpoints.CLIENT_GWT_PREFIX + Endpoints.GET_RESERVE_OFFERS)
public interface ReserveOfferService extends RemoteService {

    List<ReserveOfferDTO> getReserveOffers(ReserveOfferIdDTO criteria) throws IllegalArgumentException;

}
