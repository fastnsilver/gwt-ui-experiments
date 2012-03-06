package name.cphillipson.experimental.gwt.client.module.common.widget.popup;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

/**
 * Displays exception messages
 * @author cphillipson
 *
 */
public class ExceptionDialog extends DialogBox {

    private final Label summary;
    private final Label details;

    public ExceptionDialog(String summary, String details) {
        super();
        this.summary = new Label(summary);
        this.details = new Label(details);  // this is what we cannot retrieve b/c it is not serialized by GWT out-of-the-box

        // TODO 1) Map details to error code? 2) Provide human-friendly exception message

        setDefaults();

        show();
    }

    private void setDefaults() {
        setText(UiMessages.INSTANCE.exception());
        final VerticalPanel dialogContents = new VerticalPanel();
        dialogContents.setSpacing(4);
        setWidget(dialogContents);
        final DisclosurePanel dp = new DisclosurePanel(UiMessages.INSTANCE.details());
        dp.setContent(details);
        dialogContents.add(summary);
        dialogContents.add(dp);
        dialogContents.setCellHorizontalAlignment(
                summary, HasHorizontalAlignment.ALIGN_LEFT);
        dialogContents.setCellHorizontalAlignment(
                details, HasHorizontalAlignment.ALIGN_LEFT);
        final Button closeButton = new Button(
                UiMessages.INSTANCE.close(), new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        hide();
                    }
                });
        dialogContents.add(closeButton);
        if (LocaleInfo.getCurrentLocale().isRTL()) {
            dialogContents.setCellHorizontalAlignment(
                    closeButton, HasHorizontalAlignment.ALIGN_LEFT);

        } else {
            dialogContents.setCellHorizontalAlignment(
                    closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
        }
        setGlassEnabled(true);
        center();
        closeButton.setFocus(true);
    }

}
