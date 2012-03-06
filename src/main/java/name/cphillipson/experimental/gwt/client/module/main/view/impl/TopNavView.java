package name.cphillipson.experimental.gwt.client.module.main.view.impl;

import static com.google.gwt.query.client.GQuery.$;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.mvp4g.client.event.BaseEventHandler;
import name.cphillipson.experimental.gwt.client.module.common.service.CommonTokenService;
import name.cphillipson.experimental.gwt.client.module.common.service.TokenService;
import name.cphillipson.experimental.gwt.client.module.common.widget.nav.CustomTabBar;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.view.ITopNavView;
import name.cphillipson.experimental.gwt.client.module.main.view.ITopNavView.ITopNavPresenter;
import name.cphillipson.experimental.gwt.client.module.main.view.ReverseCompositeView;
import name.cphillipson.experimental.gwt.client.resources.UiSelectors;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

@Singleton
public class TopNavView extends ReverseCompositeView<ITopNavPresenter> implements ITopNavView {

    @UiTemplate("TopNavView.ui.xml")
    interface TopNavViewUiBinder extends UiBinder<Widget, TopNavView> { }
    private static TopNavViewUiBinder uiBinder = GWT.create(TopNavViewUiBinder.class);

    @UiField
    CustomTabBar launchPad;

    public TopNavView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void prepare(NavigationInfo info) {
        if (info != null) {
            final TokenService tokenService = new CommonTokenService<MainEventBus>((BaseEventHandler<MainEventBus>) presenter, info);
            final MainEventBus eventBus = ((BaseEventHandler<MainEventBus>) presenter).getEventBus();
            launchPad.setInput(info.allRootOptions(), tokenService, eventBus);

            // A tab is rendered as <li><a href="...">Link</a></li>
            // We want the target area to be the entire tab, therefore...
            UiSelectors.INSTANCE.getTabRow().delegate("li", Event.ONCLICK, new Function() {
                @Override
                // see core.css
                public void f(Element e) {
                    // clear the content area
                    eventBus.setContent(new HTML(""));
                    // update GWT history
                    History.newItem($(e).children().first().attr("href").split("#")[1]);
                    // remove styling of prior selected tab, basically we're "wiping" .selected from all tabs
                    UiSelectors.INSTANCE.getTabs().removeClass("selected");
                    // update selected tab's style
                    $(e).addClass("selected");
                }
            });
        }
    }

}
