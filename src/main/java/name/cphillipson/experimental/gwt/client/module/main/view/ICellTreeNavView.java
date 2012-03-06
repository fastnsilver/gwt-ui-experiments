package name.cphillipson.experimental.gwt.client.module.main.view;

import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.common.presenter.StartupPresenter;
import name.cphillipson.experimental.gwt.client.module.common.view.NavigableView;

public interface ICellTreeNavView extends NavigableView {

    public interface ICellTreeNavPresenter extends StartupPresenter {
        void onInit();
        void onPopulateLeftNav(NavTarget origin);
    }

}
