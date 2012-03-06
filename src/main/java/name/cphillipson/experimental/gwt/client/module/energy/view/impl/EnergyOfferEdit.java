package name.cphillipson.experimental.gwt.client.module.energy.view.impl;

import java.util.List;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.common.dto.MarketTypeType;
import name.cphillipson.experimental.gwt.client.module.common.view.DisplayMode;
import name.cphillipson.experimental.gwt.client.module.common.widget.grid.GridButtonBar;
import name.cphillipson.experimental.gwt.client.module.common.widget.nav.BreadcrumbTrail;
import name.cphillipson.experimental.gwt.client.module.energy.dto.EnergyOfferDTO;
import name.cphillipson.experimental.gwt.client.module.energy.view.IEnergyOfferEdit;
import name.cphillipson.experimental.gwt.client.module.energy.view.IEnergyOfferEdit.IEnergyOfferEditPresenter;
import name.cphillipson.experimental.gwt.client.module.energy.widget.EnergyOfferGrid;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.Payload;
import name.cphillipson.experimental.gwt.client.module.main.view.ReverseCompositeView;
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

public class EnergyOfferEdit extends ReverseCompositeView<IEnergyOfferEditPresenter> implements IEnergyOfferEdit {

    @UiTemplate("EnergyOffer.ui.xml")
    interface EnergyOfferEditUiBinder extends UiBinder<Widget, EnergyOfferEdit> { }
    private static EnergyOfferEditUiBinder uiBinder = GWT.create(EnergyOfferEditUiBinder.class);

    @UiField
    BreadcrumbTrail trail;

    @UiField(provided=true)
    EnergyOfferGrid hourlyValues;

    @UiField
    GridButtonBar bar;

    @Override
    public void createView() {
        hourlyValues = new EnergyOfferGrid(25, DataGridResource.INSTANCE);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void prepare(NavTarget target, List<EnergyOfferDTO> result) {
        final String rawMarketType = target.getParamValue(NodeType.MARKET_TYPE);
        final MarketTypeType mt = MarketTypeType.fromValue(rawMarketType);

        trail.setInput(target.getParamValue(NodeType.OPERATING_DAY), target.getParamValue(NodeType.RESOURCE), UiMessages.INSTANCE.energy_offer(), UiMessagesHelper.getMarketType(mt), UiMessages.INSTANCE.edit());

        hourlyValues.setInput(result, DisplayMode.EDIT);
        bar.setInput(DisplayMode.EDIT, Payload.ENERGY_OFFER, target, ((BaseEventHandler<MainEventBus>) presenter).getEventBus());
    }

}
