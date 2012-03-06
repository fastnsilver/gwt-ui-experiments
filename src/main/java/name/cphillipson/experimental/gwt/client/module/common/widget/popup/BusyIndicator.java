package name.cphillipson.experimental.gwt.client.module.common.widget.popup;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import name.cphillipson.experimental.gwt.client.resources.UiResources;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

/**
 * Shown when a view is being prepared and hidden after view is active.
 * @author cphillipson
 *
 */
public class BusyIndicator extends DecoratedPopupPanel {

    private static final String DEFAULT_BUSY_MESSAGE_STYLE = UiResources.INSTANCE.style().busy();
    private static final String DEFAULT_BUSY_MESSAGE_TEXT = UiMessages.INSTANCE.loading();
    private static final ImageResource DEFAULT_BUSY_INDICATOR_IMAGE = UiResources.INSTANCE.busy();

    private String busyIndicatorStyleName;
    private String busyIndicatorMessageText;
    private ImageResource busyIndicatorImage;

    public BusyIndicator() {
        super(false, true);
        setDefaults();
        buildPanel();
    }

    // overload message text and image
    public BusyIndicator(String busyIndicatorMessageText, ImageResource busyIndicatorImage) {
        super(false, true);
        setBusyIndicatorStyleName(DEFAULT_BUSY_MESSAGE_STYLE);
        setBusyIndicatorMessageText(busyIndicatorMessageText);
        setBusyIndicatorImage(busyIndicatorImage);
        buildPanel();
    }

    private void setDefaults() {
        setBusyIndicatorMessageText(DEFAULT_BUSY_MESSAGE_TEXT);
        setBusyIndicatorStyleName(DEFAULT_BUSY_MESSAGE_STYLE);
        setBusyIndicatorImage(DEFAULT_BUSY_INDICATOR_IMAGE);
    }

    private void buildPanel() {
        final FlowPanel msgPanel = new FlowPanel();
        final Label busyMsg = new Label(busyIndicatorMessageText);
        busyMsg.setStyleName(busyIndicatorStyleName);
        final Image indicator = new Image(busyIndicatorImage);
        indicator.setStyleName(busyIndicatorStyleName);
        msgPanel.add(indicator);
        msgPanel.add(busyMsg);
        msgPanel.setStyleName(UiResources.INSTANCE.style().busyWrapper());
        add(msgPanel);
        center();
    }

    public void setBusyIndicatorStyleName(String busyIndicatorStyleName) {
        this.busyIndicatorStyleName = busyIndicatorStyleName;
    }

    public void setBusyIndicatorMessageText(String busyIndicatorMessageText) {
        this.busyIndicatorMessageText = busyIndicatorMessageText;
    }

    public void setBusyIndicatorImage(ImageResource busyIndicatorImage) {
        this.busyIndicatorImage = busyIndicatorImage;
    }
}
