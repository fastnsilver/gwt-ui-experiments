package name.cphillipson.experimental.gwt.client.module.reserve.view.impl;

import java.util.List;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.common.dto.MarketTypeType;
import name.cphillipson.experimental.gwt.client.module.common.dto.ReserveProductType;
import name.cphillipson.experimental.gwt.client.module.common.view.DisplayMode;
import name.cphillipson.experimental.gwt.client.module.common.widget.grid.GridButtonBar;
import name.cphillipson.experimental.gwt.client.module.common.widget.nav.BreadcrumbTrail;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.Payload;
import name.cphillipson.experimental.gwt.client.module.main.view.ReverseCompositeView;
import name.cphillipson.experimental.gwt.client.module.reserve.dto.ReserveOfferDTO;
import name.cphillipson.experimental.gwt.client.module.reserve.view.IReserveOfferEdit;
import name.cphillipson.experimental.gwt.client.module.reserve.view.IReserveOfferEdit.IReserveOfferEditPresenter;
import name.cphillipson.experimental.gwt.client.module.reserve.widget.ReserveOfferGrid;
import name.cphillipson.experimental.gwt.client.resources.DataGridResource;
import name.cphillipson.experimental.gwt.client.util.UiMessagesHelper;
import name.cphillipson.experimental.gwt.shared.bean.NodeType;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.event.BaseEventHandler;

public class ReserveOfferEdit extends ReverseCompositeView<IReserveOfferEditPresenter> implements IReserveOfferEdit {

    @UiTemplate("ReserveOffer.ui.xml")
    interface ReserveOfferEditUiBinder extends UiBinder<Widget, ReserveOfferEdit> { }
    private static ReserveOfferEditUiBinder uiBinder = GWT.create(ReserveOfferEditUiBinder.class);

    @UiField
    BreadcrumbTrail trail;

    @UiField(provided=true)
    ReserveOfferGrid hourlyValues;

    @UiField
    GridButtonBar bar;

    @Override
    public void createView() {
        hourlyValues = new ReserveOfferGrid(25, DataGridResource.INSTANCE);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void prepare(NavTarget target, List<ReserveOfferDTO> result) {
        final String rawMarketType = target.getParamValue(NodeType.MARKET_TYPE);
        final MarketTypeType mt = MarketTypeType.fromValue(rawMarketType);

        final String rawProductType = target.getParamValue(NodeType.PRODUCT_TYPE);
        final ReserveProductType pt = ReserveProductType.fromValue(rawProductType);

        trail.setInput(target.getParamValue(NodeType.OPERATING_DAY), target.getParamValue(NodeType.RESOURCE), UiMessagesHelper.getReserveProductType(pt), UiMessagesHelper.getMarketType(mt), UiMessages.INSTANCE.edit());

        hourlyValues.setInput(result, DisplayMode.EDIT);
        bar.setInput(DisplayMode.EDIT, Payload.RESERVE_OFFER, target, ((BaseEventHandler<MainEventBus>) presenter).getEventBus());
    }

}
