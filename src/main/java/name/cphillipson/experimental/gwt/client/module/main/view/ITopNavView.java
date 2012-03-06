package name.cphillipson.experimental.gwt.client.module.main.view;

import name.cphillipson.experimental.gwt.client.module.common.presenter.StartupPresenter;
import name.cphillipson.experimental.gwt.client.module.common.view.NavigableView;

public interface ITopNavView extends NavigableView {

    public interface ITopNavPresenter extends StartupPresenter {
        void onPopulateTopNav();
    }
}
