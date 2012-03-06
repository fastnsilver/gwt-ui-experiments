package name.cphillipson.experimental.gwt.server.nav.stub;

import javax.inject.Inject;

import name.cphillipson.experimental.gwt.server.dao.TestData;
import name.cphillipson.experimental.gwt.server.i18n.MessageHelper;
import name.cphillipson.experimental.gwt.server.nav.AdministrationNavigationConstructor;
import name.cphillipson.experimental.gwt.server.nav.DemandNavigationConstructor;
import name.cphillipson.experimental.gwt.server.nav.MarketNavigationConstructor;
import name.cphillipson.experimental.gwt.server.nav.NavigationConstructor;
import name.cphillipson.experimental.gwt.server.nav.NavigationFactory;
import name.cphillipson.experimental.gwt.server.nav.NotificationsNavigationConstructor;
import name.cphillipson.experimental.gwt.server.nav.ReserveZoneNavigationConstructor;
import name.cphillipson.experimental.gwt.server.nav.TransactionLogNavigationConstructor;
import name.cphillipson.experimental.gwt.shared.bean.NavPerspective;

public class StubNavigationFactory implements NavigationFactory {

    @Inject
    private MessageHelper messageHelper;

    @Inject
    private TestData dataSource;

    @Override
    public NavigationConstructor getInstance(NavPerspective perspective) {
        NavigationConstructor constructor = null;
        switch (perspective) {
            case ADMINSTRATION:
                constructor = new AdministrationNavigationConstructor();
                break;
            case DEMAND:
                constructor = new DemandNavigationConstructor();
                break;
            case MARKET:
                constructor = new MarketNavigationConstructor();
                break;
            case NOTIFICATONS:
                constructor = new NotificationsNavigationConstructor();
                break;
            case RESERVE_ZONE:
                constructor = new ReserveZoneNavigationConstructor();
                break;
            case SUPPLY:
                constructor = new StubSupplyNavigationConstructor();
                ((StubDataProvider) constructor).setDataSource(dataSource);
                break;
            case TRANSACTION_LOG:
                constructor = new TransactionLogNavigationConstructor();
                break;
            case VIRTUAL:
                constructor = new StubVirtualNavigationConstructor();
                ((StubDataProvider) constructor).setDataSource(dataSource);
                break;
        }
        constructor.setMessageHelper(messageHelper);
        return constructor;
    }

}
