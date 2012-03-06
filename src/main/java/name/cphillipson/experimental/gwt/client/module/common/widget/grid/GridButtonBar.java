package name.cphillipson.experimental.gwt.client.module.common.widget.grid;

import java.util.Set;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;
import name.cphillipson.experimental.gwt.client.module.common.view.DisplayMode;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.module.main.Payload;
import name.cphillipson.experimental.gwt.client.resources.UiResources;

/**
 * For use with {@link ToggleableGrid}-based views.
 * Composite of {@link ActionBar} and {@link SubmitBar}.
 * @author cphillipson
 *
 */
public class GridButtonBar extends FlowPanel {

    private static final String DEFAULT_BUTTON_BAR_STYLE_NAME = UiResources.INSTANCE.style().gridButtonBar();

    private ActionBar actions;
    private SubmitBar submitActions;

    public GridButtonBar() {
        super();
        actions = new ActionBar();
        submitActions = new SubmitBar();
        setDefaults();
    }

    private void addButtons(Set<Button> buttons) {
        for (final Button btn: buttons) {
            add(btn);
        }
    }

    private void setDefaults() {
        setButtonBarStyleName(DEFAULT_BUTTON_BAR_STYLE_NAME);
    }

    public <E extends MainEventBus> void setInput(DisplayMode mode, Payload type, NavTarget target, E eventBus) {
        actions.setInput(mode, type, target, eventBus);
        submitActions.setInput(mode, type, target, eventBus);

        addButtons(actions.getButtons());
        addButtons(submitActions.getButtons());
    }

    public void setButtonBarStyleName(String buttonBarStyleName) {
        setStyleName(buttonBarStyleName);
    }
}
