package name.cphillipson.experimental.gwt.client.module.main.view.impl;

import name.cphillipson.experimental.gwt.client.module.common.bean.AboutInfo;
import name.cphillipson.experimental.gwt.client.resources.UiResources;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Displays information about this MUI instance
 * @author cphillipson
 *
 */
public class AboutDialog extends DialogBox {

    final AboutInfo about;

    public AboutDialog(AboutInfo about) {
        super();
        this.about = about;
        setDefaults();

        show();
    }

    private void setDefaults() {
        setText(UiMessages.INSTANCE.about());
        final VerticalPanel dialogContents = new VerticalPanel();
        dialogContents.setSpacing(4);
        setWidget(dialogContents);
        final SafeHtmlBuilder sb = new SafeHtmlBuilder();

        final String flair = "<a href=\"http://stackoverflow.com/users/945660/chris-phillipson\"><img src=\"http://stackoverflow.com/users/flair/945660.png\" width=\"208\" height=\"58\" alt=\"profile for Chris Phillipson at Stack Overflow, Q&A for professional and enthusiast programmers\" title=\"profile for Chris Phillipson at Stack Overflow, Q&A for professional and enthusiast programmers\"></a>";
        final HTMLPanel html = new HTMLPanel(flair);

        dialogContents.add(html);

        final StringBuilder b = new StringBuilder();
        b.append(about.getAppName())
        .append("\n")
        .append(UiMessages.INSTANCE.version())
        .append(": ")
        .append(about.getVersion())
        .append("\n")
        .append(UiMessages.INSTANCE.hostname())
        .append(": ")
        .append(about.getHostname())
        .append("\n")
        .append(UiMessages.INSTANCE.database())
        .append(": ")
        .append(about.getDatabase())
        .append("\n");

        // TODO add any licensing/software terms text here?

        sb.appendHtmlConstant("<p>")
        .appendEscapedLines(b.toString())
        .appendHtmlConstant("</p>");

        final HTML contents = new HTML(sb.toSafeHtml());
        dialogContents.add(contents);

        dialogContents.setCellHorizontalAlignment(
                html, HasHorizontalAlignment.ALIGN_LEFT);
        dialogContents.setCellHorizontalAlignment(
                contents, HasHorizontalAlignment.ALIGN_LEFT);

        final Button closeButton = new Button(
                UiMessages.INSTANCE.close(), new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        hide();
                    }
                });
        closeButton.setStyleName(UiResources.INSTANCE.style().gridButtonBar());
        dialogContents.add(closeButton);
        if (LocaleInfo.getCurrentLocale().isRTL()) {
            dialogContents.setCellHorizontalAlignment(
                    closeButton, HasHorizontalAlignment.ALIGN_LEFT);

        } else {
            dialogContents.setCellHorizontalAlignment(
                    closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
        }
        center();
        closeButton.setFocus(true);
    }

}
