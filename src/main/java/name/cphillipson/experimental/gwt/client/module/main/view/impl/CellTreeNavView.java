package name.cphillipson.experimental.gwt.client.module.main.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.mvp4g.client.event.BaseEventHandler;
import name.cphillipson.experimental.gwt.client.module.common.widget.nav.NavPanel;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.view.ICellTreeNavView;
import name.cphillipson.experimental.gwt.client.module.main.view.ICellTreeNavView.ICellTreeNavPresenter;
import name.cphillipson.experimental.gwt.client.module.main.view.ReverseCompositeView;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

@Singleton
public class CellTreeNavView extends ReverseCompositeView<ICellTreeNavPresenter> implements ICellTreeNavView {

    @UiTemplate("CellTreeNavView.ui.xml")
    interface CellTreeNavViewUiBinder extends UiBinder<Widget, CellTreeNavView> { }
    private static CellTreeNavViewUiBinder uiBinder = GWT.create(CellTreeNavViewUiBinder.class);

    @UiField
    NavPanel navPanel;

    public CellTreeNavView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void prepare(NavigationInfo info) {
        if (info != null) {
            navPanel.setInput(info, (BaseEventHandler<MainEventBus>) presenter);
        }
    }

}
