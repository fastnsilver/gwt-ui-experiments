package name.cphillipson.experimental.gwt.client.module.main.view;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import name.cphillipson.experimental.gwt.client.module.common.presenter.StartupPresenter;
import name.cphillipson.experimental.gwt.shared.bean.MessageInfo;

public interface IMessagesView extends IsWidget {

    public interface IMessagesPresenter extends StartupPresenter {
        void onPopulateMessages();
    }

    void prepare(List<MessageInfo> info);
}
