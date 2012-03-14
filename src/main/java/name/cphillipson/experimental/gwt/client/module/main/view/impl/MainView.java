package name.cphillipson.experimental.gwt.client.module.main.view.impl;

import name.cphillipson.experimental.gwt.client.module.main.view.IMainView;
import name.cphillipson.experimental.gwt.client.module.main.view.IMainView.IMainPresenter;
import name.cphillipson.experimental.gwt.client.module.main.view.ReverseCompositeView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class MainView extends ReverseCompositeView<IMainPresenter> implements IMainView {

    @UiTemplate("MainView.ui.xml")
    interface MainViewUiBinder extends UiBinder<Widget, MainView> { }
    private static MainViewUiBinder uiBinder = GWT.create(MainViewUiBinder.class);

    @UiField(provided = true)
    Widget banner, topNav, leftNav;

    @UiField
    SimplePanel content;


    @Inject
    public MainView(BannerView banner, TopNavView topNav, CellTreeNavView leftNav) {
        this.banner = banner;
        this.topNav = topNav;
        this.leftNav = leftNav;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setContent(IsWidget content) {
        this.content.setWidget(content);
    }

}