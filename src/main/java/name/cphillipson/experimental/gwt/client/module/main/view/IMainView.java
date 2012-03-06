package name.cphillipson.experimental.gwt.client.module.main.view;

import com.google.gwt.user.client.ui.IsWidget;

public interface IMainView extends IsWidget {

    public interface IMainPresenter {
        void onInit();
        void onHandle(Throwable caught);
        void onShow404();
        void onSetContent(IsWidget content);
    }

    void setContent(IsWidget content);
}
