package name.cphillipson.experimental.gwt.server.nav;

import javax.inject.Inject;

import name.cphillipson.experimental.gwt.server.i18n.MessageHelper;
import name.cphillipson.experimental.gwt.shared.bean.NavPerspective;

public class DefaultNavigationFactory implements NavigationFactory {

    @Inject
    private MessageHelper messageHelper;

    @Override
    public NavigationConstructor getInstance(NavPerspective perspective) {
        NavigationConstructor constructor = null;
        switch(perspective) {
            case ADMINSTRATION: constructor = new AdministrationNavigationConstructor(); break;
            case DEMAND: constructor = new DemandNavigationConstructor(); break;
            case MARKET: constructor = new MarketNavigationConstructor(); break;
            case NOTIFICATONS: constructor = new NotificationsNavigationConstructor(); break;
            case RESERVE_ZONE: constructor = new ReserveZoneNavigationConstructor(); break;
            case SUPPLY: constructor = new SupplyNavigationConstructor(); break;
            case TRANSACTION_LOG: constructor = new TransactionLogNavigationConstructor(); break;
            case VIRTUAL: constructor = new VirtualNavigationConstructor(); break;
        }
        constructor.setMessageHelper(messageHelper);
        return constructor;
    }

}
