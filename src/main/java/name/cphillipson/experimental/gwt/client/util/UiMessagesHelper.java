package name.cphillipson.experimental.gwt.client.util;




import name.cphillipson.experimental.gwt.client.module.common.dto.MarketTypeType;
import name.cphillipson.experimental.gwt.client.module.common.dto.ReserveProductType;
import name.cphillipson.experimental.gwt.shared.bean.Operation;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;


/**
 * Helps lookup localized values based on a constant or enumerated type.
 * @author cphillipson
 *
 */
public class UiMessagesHelper {

    public static String getReserveProductType(ReserveProductType type) {
        String result = null;
        switch (type) {
            case REGDN: result = UiMessages.INSTANCE.regulation_offer_up(); break;
            case REGUP: result = UiMessages.INSTANCE.regulation_offer_up(); break;
            case SPIN: result = UiMessages.INSTANCE.spinning_offer(); break;
            case SUPP: result = UiMessages.INSTANCE.supplemental_offer(); break;
        }
        return result;
    }

    public static String getMarketType(MarketTypeType type) {
        return getMarketType(type, false);
    }

    public static String getMarketType(MarketTypeType type, boolean isShortForm) {
        String result = null;
        switch (type) {
            case DAMKT:
                if (isShortForm) {
                    result = UiMessages.INSTANCE.day_ahead_market_short_form();
                } else {
                    result = UiMessages.INSTANCE.day_ahead_market();
                }
                break;
            case RTBM:
                if (isShortForm) {
                    result = UiMessages.INSTANCE.real_time_balancing_market_short_form();
                } else {
                    result = UiMessages.INSTANCE.real_time_balancing_market();
                }

                break;
        }
        return result;
    }

    public static String getOperation(Operation op) {
        String result = null;
        switch (op) {
            case VIEW:
                result = UiMessages.INSTANCE.view();
                break;
            case ADD:
                result = UiMessages.INSTANCE.add();
                break;
            case COPY:
                result = UiMessages.INSTANCE.copy();
                break;
            case DELETE:
                result = UiMessages.INSTANCE.delete();
                break;
            case DOWNLOAD:
                result = UiMessages.INSTANCE.download();
                break;
            case EDIT:
                result = UiMessages.INSTANCE.edit();
                break;
            case RESET:
                result = UiMessages.INSTANCE.reset();
                break;
            case SAVE:
                result = UiMessages.INSTANCE.submit();
                break;
            case UPLOAD:
                result = UiMessages.INSTANCE.upload();
                break;
        }

        return result;
    }

}
