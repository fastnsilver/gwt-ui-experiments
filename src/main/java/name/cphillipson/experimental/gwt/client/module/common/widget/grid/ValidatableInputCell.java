package name.cphillipson.experimental.gwt.client.module.common.widget.grid;

import com.google.gwt.cell.client.AbstractInputCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesUtils;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import name.cphillipson.experimental.gwt.client.resources.UiResources;

/**
 * <p>A cell that will update its styling and provide feedback upon a validation constraint violation.</p>
 * <p>Implementation based upon GWT Showcase's <a href="http://gwt.google.com/samples/Showcase/Showcase.html#!CwCellValidation">Cell Validation</a> example.</p>
 * @author cphillipson
 *
 */
public class ValidatableInputCell extends AbstractInputCell<String, ValidatableInputCell.ValidationData> {

    interface Template extends SafeHtmlTemplates {
        @Template("<input type=\"text\" value=\"{0}\" size=\"{1}\" style=\"{2}\" tabindex=\"-1\"></input>")
        SafeHtml input(String value, String width, SafeStyles color);
    }

    private static Template template;

    /**
     * The error message to be displayed as a pop-up near the field
     */
    private String errorMessage;

    private static final int DEFAULT_INPUT_SIZE = 15;

    /**
     * Specifies the width, in characters, of the &lt;input&gt; element contained within this cell
     */
    private int inputSize = DEFAULT_INPUT_SIZE;

    public ValidatableInputCell() {
        super("change", "keyup");
        if (template == null) {
            template = GWT.create(Template.class);
        }
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = SafeHtmlUtils.htmlEscape(errorMessage);
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, String value,
            NativeEvent event, ValueUpdater<String> valueUpdater) {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);

        // Ignore events that don't target the input.
        final InputElement input = (InputElement) getInputElement(parent);
        final Element target = event.getEventTarget().cast();
        if (!input.isOrHasChild(target)) {
            return;
        }

        final Object key = context.getKey();
        final String eventType = event.getType();

        if ("change".equals(eventType)) {
            finishEditing(parent, value, key, valueUpdater);
        } else if ("keyup".equals(eventType)) {
            // Mark cell as containing a pending change
            input.getStyle().setColor("blue");

            ValidationData viewData = getViewData(key);
            // Save the new value in the view data.
            if (viewData == null) {
                viewData = new ValidationData();
                setViewData(key, viewData);
            }
            final String newValue = input.getValue();
            viewData.setValue(newValue);
            finishEditing(parent, newValue, key, valueUpdater);

            // Update the value updater, which updates the field updater.
            if (valueUpdater != null) {
                valueUpdater.update(newValue);
            }
        }
    }

    @Override
    public void render(Context context, String value, SafeHtmlBuilder sb) {
        // Get the view data.
        final Object key = context.getKey();
        ValidationData viewData = getViewData(key);
        if (viewData != null && viewData.getValue().equals(value)) {
            // Clear the view data if the value is the same as the current value.
            clearViewData(key);
            viewData = null;
        }

        /*
         * If viewData is null, just paint the contents black. If it is non-null,
         * show the pending value and paint the contents red if they are known to
         * be invalid.
         */
        final String pendingValue = viewData == null ? null : viewData.getValue();
        final boolean invalid = viewData == null ? false : viewData.isInvalid();

        final String color = pendingValue != null ? invalid ? "red" : "blue" : "black";
        final SafeStyles safeColor = SafeStylesUtils.fromTrustedString("color: " + color + ";");
        sb.append(template.input(pendingValue != null ? pendingValue : value, String.valueOf(inputSize), safeColor));
    }

    @Override
    protected void onEnterKeyDown(Context context, Element parent, String value,
            NativeEvent event, ValueUpdater<String> valueUpdater) {
        final Element target = event.getEventTarget().cast();
        if (getInputElement(parent).isOrHasChild(target)) {
            finishEditing(parent, value, context.getKey(), valueUpdater);
        } else {
            super.onEnterKeyDown(context, parent, value, event, valueUpdater);
        }
    }

    @Override
    protected void finishEditing(Element parent, String value, Object key,
            ValueUpdater<String> valueUpdater) {
        final ValidationData viewData = getViewData(key);

        final String pendingValue = viewData == null ? null : viewData.getValue();
        final boolean invalid = viewData == null ? false : viewData.isInvalid();

        if (invalid) {
            final DecoratedPopupPanel errorMessagePopup = new DecoratedPopupPanel(true);
            final FlowPanel messageContainer = new FlowPanel();
            messageContainer.setWidth("200px");
            final Label messageTxt = new Label(errorMessage, true);
            messageTxt.setStyleName(UiResources.INSTANCE.style().error());
            messageContainer.add(messageTxt);
            errorMessagePopup.setWidget(messageContainer);

            // Reposition the popup relative to input field
            final int left = parent.getAbsoluteRight() + 25;
            final int top = parent.getAbsoluteTop();

            errorMessagePopup.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
                @Override
                public void setPosition(int offsetWidth, int offsetHeight) {
                    errorMessagePopup.setPopupPosition(left, top);
                }
            });
        }
        // XXX let user continue or force focus until value is valid? for now the former is implemented
        super.finishEditing(parent, pendingValue, key, valueUpdater);
    }

    /**
     * The ViewData used by {@link ValidatableInputCell}.
     */
    static class ValidationData {
        private boolean invalid;
        private String value;

        public String getValue() {
            return value;
        }

        public boolean isInvalid() {
            return invalid;
        }

        public void setInvalid(boolean invalid) {
            this.invalid = invalid;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
