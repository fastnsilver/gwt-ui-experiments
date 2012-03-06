package name.cphillipson.experimental.gwt.client.module.main.presenter;

import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.SafeCallback;
import name.cphillipson.experimental.gwt.client.module.main.rpc.NavigationServiceAsync;
import name.cphillipson.experimental.gwt.client.module.main.view.ICellTreeNavView;
import name.cphillipson.experimental.gwt.client.module.main.view.ICellTreeNavView.ICellTreeNavPresenter;
import name.cphillipson.experimental.gwt.client.module.main.view.impl.CellTreeNavView;
import name.cphillipson.experimental.gwt.shared.bean.NavPerspective;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

@Presenter(view = CellTreeNavView.class)
public class CellTreeNavPresenter extends BasePresenter<ICellTreeNavView, MainEventBus> implements ICellTreeNavPresenter {

    @Inject
    NavigationServiceAsync navigationService;

    @Override
    public void onInit() {
        // do nothing
    }

    @Override
    public void onStart() {
        eventBus.populateLeftNav(NavPerspective.toNavTarget(NavPerspective.getDefault()));
    }

    @Override
    public void onPopulateLeftNav(NavTarget origin) {

        navigationService.getNavInfo(NavPerspective.fromNavTarget(origin), new SafeCallback<NavigationInfo, ICellTreeNavView>(view, eventBus) {

            @Override
            public void onSuccessImpl(NavigationInfo info) {
                view.prepare(info);
            }

        });

    }

}
