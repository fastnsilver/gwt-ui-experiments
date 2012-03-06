package name.cphillipson.experimental.gwt.client.module.main.presenter;

import java.util.ArrayList;
import java.util.List;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import name.cphillipson.experimental.gwt.client.module.common.widget.popup.BusyIndicator;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.view.IMessagesView;
import name.cphillipson.experimental.gwt.client.module.main.view.IMessagesView.IMessagesPresenter;
import name.cphillipson.experimental.gwt.client.module.main.view.impl.MessagesView;
import name.cphillipson.experimental.gwt.shared.bean.MessageInfo;

@Presenter(view = MessagesView.class)
public class MessagesPresenter extends BasePresenter<IMessagesView, MainEventBus> implements IMessagesPresenter {

    @Override
    public void onStart() {
        eventBus.populateMessages();
    }

    @Override
    public void onPopulateMessages() {
        final BusyIndicator indicator = new BusyIndicator();
        indicator.show();

        // FIXME Plumb a real service with real data
        final MessageInfo info = new MessageInfo();
        info.setId("1");
        info.setPriority("HIGH");
        info.setText("Hello... you are looking at the messages pane aren't you?");
        final List<MessageInfo> messages = new ArrayList<MessageInfo>();
        messages.add(info);

        view.prepare(messages);

        indicator.hide();
    }
}
