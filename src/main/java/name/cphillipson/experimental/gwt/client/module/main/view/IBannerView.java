package name.cphillipson.experimental.gwt.client.module.main.view;

import com.google.gwt.user.client.ui.IsWidget;
import name.cphillipson.experimental.gwt.client.module.common.presenter.StartupPresenter;
import name.cphillipson.experimental.gwt.shared.bean.BannerInfo;

public interface IBannerView extends IsWidget {

    public interface IBannerPresenter extends StartupPresenter {
        void onPopulateBanner();
    }

    void prepare(BannerInfo info);
}
