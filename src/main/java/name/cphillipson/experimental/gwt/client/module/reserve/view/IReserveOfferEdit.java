package name.cphillipson.experimental.gwt.client.module.reserve.view;

import java.util.List;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.common.view.TargetedView;
import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferDTO;

public interface IReserveOfferEdit extends TargetedView<List<ReserveOfferDTO>> {

    public interface IReserveOfferEditPresenter {
        void onEditReserveOffer(NavTarget origin);
    }

}
