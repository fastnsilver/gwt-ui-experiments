package name.cphillipson.experimental.gwt.client.module.energy.view;

import java.util.List;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.common.view.TargetedView;
import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferDTO;

public interface IEnergyOfferEdit extends TargetedView<List<EnergyOfferDTO>> {

    public interface IEnergyOfferEditPresenter {
        void onEditEnergyOffer(NavTarget origin);
    }

}
