package name.cphillipson.experimental.gwt.client.module.main;


import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.energy.presenter.EnergyOfferEditPresenter;
import name.cphillipson.experimental.gwt.client.module.energy.presenter.EnergyOfferViewPresenter;
import name.cphillipson.experimental.gwt.client.module.main.history.SimpleHistoryConverter;
import name.cphillipson.experimental.gwt.client.module.main.presenter.BannerPresenter;
import name.cphillipson.experimental.gwt.client.module.main.presenter.CellTreeNavPresenter;
import name.cphillipson.experimental.gwt.client.module.main.presenter.MainPresenter;
import name.cphillipson.experimental.gwt.client.module.main.presenter.MessagesPresenter;
import name.cphillipson.experimental.gwt.client.module.main.presenter.TopNavPresenter;
import name.cphillipson.experimental.gwt.client.module.reserve.presenter.ReserveOfferEditPresenter;
import name.cphillipson.experimental.gwt.client.module.reserve.presenter.ReserveOfferViewPresenter;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.NotFoundHistory;
import com.mvp4g.client.annotation.PlaceService;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBusWithLookup;

@Debug
@PlaceService(EmktPlaceService.class)
@Events(module = MainModule.class, startPresenter = MainPresenter.class, historyOnStart = true)
public interface MainEventBus extends EventBusWithLookup {

    // bootstrap

    // XXX 2012-02-11 MessagesPresenter will be responsible for displaying messages
    // Consider making it a LazyPresenter instead
    // The MessagesView should change into a pop-up dialog.

    @Start
    @Event(handlers = { BannerPresenter.class, TopNavPresenter.class, CellTreeNavPresenter.class } )
    void start();

    @InitHistory
    @Event(handlers = { MainPresenter.class, CellTreeNavPresenter.class })
    void init();


    // layout events that occur at startup

    @Event(handlers = MessagesPresenter.class)
    void populateMessages();

    @Event(handlers = BannerPresenter.class)
    void populateBanner();

    @Event(handlers = TopNavPresenter.class)
    void populateTopNav();

    // note: populateLeftNav (below) is also a startup layout event


    // layout events that occur after startup

    @Event(handlers = MainPresenter.class)
    void setContent(IsWidget content);


    // place events

    @Event(handlers = CellTreeNavPresenter.class, historyConverter = SimpleHistoryConverter.class, name="nav", navigationEvent = true)
    String populateLeftNav(NavTarget origin);

    @Event(handlers = ReserveOfferViewPresenter.class, historyConverter = SimpleHistoryConverter.class, navigationEvent = true)
    String viewReserveOffer(NavTarget origin);

    @Event(handlers = ReserveOfferEditPresenter.class, historyConverter = SimpleHistoryConverter.class, navigationEvent = true)
    String editReserveOffer(NavTarget origin);

    @Event(handlers = EnergyOfferViewPresenter.class, historyConverter = SimpleHistoryConverter.class, navigationEvent = true)
    String viewEnergyOffer(NavTarget origin);

    @Event(handlers = EnergyOfferEditPresenter.class, historyConverter = SimpleHistoryConverter.class, navigationEvent = true)
    String editEnergyOffer(NavTarget origin);

    @NotFoundHistory
    @Event(handlers = MainPresenter.class)
    void show404();


    // exceptions

    @Event(handlers = MainPresenter.class)
    void handle(Throwable caught);
}
