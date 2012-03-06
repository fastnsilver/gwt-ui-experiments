package name.cphillipson.experimental.gwt.client.module.reserve.rpc;

import java.util.List;

import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferDTO;
import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferIdDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The asynchronous counterpart to <code>ReserveOfferService</code>.
 * @author cphillipson
 */
public interface ReserveOfferServiceAsync {

    void getReserveOffers(ReserveOfferIdDTO criteria, AsyncCallback<List<ReserveOfferDTO>> callback);
}
