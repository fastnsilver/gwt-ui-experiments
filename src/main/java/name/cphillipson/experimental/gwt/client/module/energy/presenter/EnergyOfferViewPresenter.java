package name.cphillipson.experimental.gwt.client.module.energy.presenter;

import java.util.List;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferDTO;
import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferIdDTO;
import name.cphillipson.experimental.gwt.client.module.energy.rpc.EnergyOfferServiceAsync;
import name.cphillipson.experimental.gwt.client.module.energy.view.IEnergyOfferView;
import name.cphillipson.experimental.gwt.client.module.energy.view.IEnergyOfferView.IEnergyOfferViewPresenter;
import name.cphillipson.experimental.gwt.client.module.energy.view.impl.EnergyOfferView;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.SafeCallback;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;

import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;

@Presenter(view = EnergyOfferView.class)
public class EnergyOfferViewPresenter extends LazyPresenter<IEnergyOfferView, MainEventBus> implements IEnergyOfferViewPresenter{

    @Inject
    EnergyOfferServiceAsync energyOfferService;

    @Override
    public void onViewEnergyOffer(final NavTarget origin) {

        // TODO Move criteria construction into a util class
        // idea: implement some sort of client-side conversion service that can be injected
        // which might be invoked like CSConversionService.convert(origin, new EnergyOfferIdDTO());
        final EnergyOfferIdDTO criteria = new EnergyOfferIdDTO();
        criteria.setDateTime(origin.getParamValue(NodeType.OPERATING_DAY));
        criteria.setMarketType(origin.getParamValue(NodeType.MARKET_TYPE));
        criteria.setResourceName(origin.getParamValue(NodeType.RESOURCE));

        energyOfferService.getEnergyOffers(criteria, new SafeCallback<List<EnergyOfferDTO>, IEnergyOfferView>(view, eventBus) {

            @Override
            public void onSuccessImpl(List<EnergyOfferDTO> result) {
                view.prepare(origin, result);
                eventBus.setContent(view);
            }

        });

    }

}
