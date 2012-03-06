package name.cphillipson.experimental.gwt.client.module.main.presenter;

import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.SafeCallback;
import name.cphillipson.experimental.gwt.client.module.main.rpc.HeaderServiceAsync;
import name.cphillipson.experimental.gwt.client.module.main.view.IBannerView;
import name.cphillipson.experimental.gwt.client.module.main.view.IBannerView.IBannerPresenter;
import name.cphillipson.experimental.gwt.client.module.main.view.impl.BannerView;
import name.cphillipson.experimental.gwt.shared.bean.BannerInfo;

@Presenter(view = BannerView.class)
public class BannerPresenter extends BasePresenter<IBannerView, MainEventBus> implements IBannerPresenter {

    @Inject
    HeaderServiceAsync headerService;

    @Override
    public void onStart() {
        eventBus.populateBanner();
    }

    @Override
    public void onPopulateBanner() {
        headerService.getBannerInfo(new SafeCallback<BannerInfo, IBannerView>(view, eventBus) {

            @Override
            public void onSuccessImpl(BannerInfo info) {
                view.prepare(info);
            }

        });

    }
}
