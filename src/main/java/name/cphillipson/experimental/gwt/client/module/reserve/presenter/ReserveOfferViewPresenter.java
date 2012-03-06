package name.cphillipson.experimental.gwt.client.module.reserve.presenter;

import java.util.List;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.SafeCallback;
import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferDTO;
import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferIdDTO;
import name.cphillipson.experimental.gwt.client.module.reserve.rpc.ReserveOfferServiceAsync;
import name.cphillipson.experimental.gwt.client.module.reserve.view.IReserveOfferView;
import name.cphillipson.experimental.gwt.client.module.reserve.view.IReserveOfferView.IReserveOfferViewPresenter;
import name.cphillipson.experimental.gwt.client.module.reserve.view.impl.ReserveOfferView;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;

import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Presenter(view = ReserveOfferView.class)
public class ReserveOfferViewPresenter extends LazyPresenter<IReserveOfferView, MainEventBus> implements IReserveOfferViewPresenter{

    @Inject
    ReserveOfferServiceAsync reserveOfferService;

    @Override
    public void onViewReserveOffer(final NavTarget origin) {

        // TODO Move criteria construction into a util class
        // idea: implement some sort of client-side conversion service that can be injected
        // which might be invoked like CSConversionService.convert(origin, new ReserveOfferIdDTO());
        final ReserveOfferIdDTO criteria = new ReserveOfferIdDTO();
        criteria.setDate(origin.getParamValue(NodeType.OPERATING_DAY));
        criteria.setMarketType(origin.getParamValue(NodeType.MARKET_TYPE));
        criteria.setProductType(origin.getParamValue(NodeType.PRODUCT_TYPE));
        criteria.setResourceName(origin.getParamValue(NodeType.RESOURCE));

        reserveOfferService.getReserveOffers(criteria, new SafeCallback<List<ReserveOfferDTO>, IReserveOfferView>(view, eventBus) {

            @Override
            public void onSuccessImpl(List<ReserveOfferDTO> result) {
                view.prepare(origin, result);
                eventBus.setContent(view);
            }

        });

    }

}
