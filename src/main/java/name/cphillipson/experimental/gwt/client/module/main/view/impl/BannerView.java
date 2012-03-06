package name.cphillipson.experimental.gwt.client.module.main.view.impl;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import name.cphillipson.experimental.gwt.client.module.common.bean.AboutInfo;
import name.cphillipson.experimental.gwt.client.module.main.view.IBannerView;
import name.cphillipson.experimental.gwt.client.module.main.view.IBannerView.IBannerPresenter;
import name.cphillipson.experimental.gwt.client.module.main.view.ReverseCompositeView;
import name.cphillipson.experimental.gwt.shared.bean.BannerInfo;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

@Singleton
public class BannerView extends ReverseCompositeView<IBannerPresenter> implements IBannerView {

    @UiTemplate("BannerView.ui.xml")
    interface BannerViewUiBinder extends UiBinder<Widget, BannerView> { }
    private static BannerViewUiBinder uiBinder = GWT.create(BannerViewUiBinder.class);

    private AboutInfo about;

    @UiField
    Anchor title;

    @UiField
    Label user, currentTime;

    private Timer timer = null;
    private final DateTimeFormat format = DateTimeFormat.getFormat(UiMessages.INSTANCE.date_time_format());

    public BannerView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void prepare(BannerInfo info) {
        buildTimer();
        title.setText(UiMessages.INSTANCE.app_title());
        title.setTitle(UiMessages.INSTANCE.app_title());
        user.setText(info.getCurrentUser());
        about = new AboutInfo(info.getAppName(), info.getVersion(), info.getHostname(), info.getDatabase());
    }

    private void buildTimer() {
        if (timer != null) {
            return;
        }
        timer = new Timer() {
            @Override
            public void run() {
                // Update clock.
                currentTime.setText(format.format(new Date()));
            }
        };
        timer.run();
        timer.scheduleRepeating(1000);
    }

    @UiHandler("title")
    void handleClick(ClickEvent event) {
        new AboutDialog(about);
    }

}
