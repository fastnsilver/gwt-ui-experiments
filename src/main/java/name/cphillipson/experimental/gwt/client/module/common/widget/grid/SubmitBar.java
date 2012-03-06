package name.cphillipson.experimental.gwt.client.module.common.widget.grid;

import com.google.gwt.user.client.ui.Button;
import name.cphillipson.experimental.gwt.shared.bean.Operation;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

/**
 * A series of buttons used to:
 * <ul><li>submit data from a grid</li><li>reset the grid to its last known state before updates occurred</li></ul>.
 * @author cphillipson
 *
 */
public class SubmitBar extends AbstractButtonBar {

    private Button saveBtn, resetBtn;

    @Override
    protected void setDefaults() {
        saveBtn = new Button(UiMessages.INSTANCE.submit());
        resetBtn = new Button(UiMessages.INSTANCE.reset());

        addButton(Operation.SAVE, saveBtn);
        addButton(Operation.RESET, resetBtn);
    }

}
