package name.cphillipson.experimental.gwt.client.module.main.history;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.History.HistoryConverterType;
import com.mvp4g.client.history.HistoryConverter;
import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.util.NavTargetUtil;

@History(type = HistoryConverterType.SIMPLE )
public class SimpleHistoryConverter implements HistoryConverter<MainEventBus> {

    // see http://code.google.com/p/mvp4g/wiki/PlaceService#Associate_a_History_Converter_to_an_event
    public String convertToToken(String eventName, NavTarget origin) {
        return origin.toUri();
    }

    @Override
    public void convertFromToken(String eventName, String token, MainEventBus eventBus) {
        eventBus.dispatch(eventName, NavTargetUtil.toNavTarget(eventName, token));
    }

    @Override
    public boolean isCrawlable() {
        return false;
    }

}
