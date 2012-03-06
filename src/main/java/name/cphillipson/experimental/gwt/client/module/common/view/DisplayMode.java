package name.cphillipson.experimental.gwt.client.module.common.view;

import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

/**
 * UI components can be in one of two states: view mode (or read-only) or edit mode (or editable).
 * @author cphillipson
 *
 */
public enum DisplayMode {

    ALL(UiMessages.INSTANCE.all()),
    EDIT(UiMessages.INSTANCE.edit()),
    VIEW(UiMessages.INSTANCE.view());

    private String mode;

    private DisplayMode(String mode) {
        this.mode = mode;
    }

    public String getValue() {
        return mode;
    }
}
