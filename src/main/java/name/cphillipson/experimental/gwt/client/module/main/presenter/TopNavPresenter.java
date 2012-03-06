package name.cphillipson.experimental.gwt.client.module.main.presenter;

import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.SafeCallback;
import name.cphillipson.experimental.gwt.client.module.main.rpc.HeaderServiceAsync;
import name.cphillipson.experimental.gwt.client.module.main.view.ITopNavView;
import name.cphillipson.experimental.gwt.client.module.main.view.ITopNavView.ITopNavPresenter;
import name.cphillipson.experimental.gwt.client.module.main.view.impl.TopNavView;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

@Presenter(view = TopNavView.class)
public class TopNavPresenter extends BasePresenter<ITopNavView, MainEventBus> implements ITopNavPresenter {

    @Inject
    HeaderServiceAsync headerService;

    @Override
    public void onStart() {
        eventBus.populateTopNav();
    }

    @Override
    public void onPopulateTopNav() {

        headerService.getTopNavInfo(new SafeCallback<NavigationInfo, ITopNavView>(view, eventBus) {

            @Override
            public void onSuccessImpl(NavigationInfo info) {
                view.prepare(info);
            }

        });

    }

}
