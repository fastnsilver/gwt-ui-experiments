package name.cphillipson.experimental.gwt.client.module.common.widget.grid;

import com.google.gwt.user.client.ui.Button;
import name.cphillipson.experimental.gwt.shared.bean.Operation;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

/**
 * A series of buttons used to act upon the contents of a grid.
 * @author cphillipson
 *
 */
public class ActionBar extends AbstractButtonBar {

    private Button viewBtn, editBtn, copyBtn, deleteBtn, downloadBtn;

    @Override
    protected void setDefaults() {
        viewBtn = new Button(UiMessages.INSTANCE.view());
        editBtn = new Button(UiMessages.INSTANCE.edit());
        copyBtn = new Button(UiMessages.INSTANCE.copy());
        deleteBtn = new Button(UiMessages.INSTANCE.delete());
        downloadBtn = new Button(UiMessages.INSTANCE.download());

        addButton(Operation.VIEW, viewBtn);
        addButton(Operation.EDIT, editBtn);
        addButton(Operation.COPY, copyBtn);
        addButton(Operation.DELETE, deleteBtn);
        addButton(Operation.DOWNLOAD, downloadBtn);
    }

}
