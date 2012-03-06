package name.cphillipson.experimental.gwt.client.module.common.widget.suggest;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;

/**
 * A layout container for a {@link MultivalueSuggestBox}.  Concrete implementations must provide a REST endpointUrl and a GWT Button
 * that has a {@link ClickHandler} attached.
 * @author cphillipson
 *
 */
public abstract class MultivalueSuggestBoxLayoutContainer extends FlowPanel {

    public MultivalueSuggestBoxLayoutContainer(String restEndpointUrl, Button sendButton) {

        final MultivalueSuggestBox multivalueField = new MultivalueSuggestBox(restEndpointUrl, true);
        final Label valueLabel = new Label();
        final HTML valueHtml = new HTML();

        final FlowPanel multivalueFieldContainer = new FlowPanel();
        multivalueFieldContainer.getElement().setId("multivalueFieldContainer");
        multivalueFieldContainer.add(multivalueField);

        final FlowPanel sendButtonContainer = new FlowPanel();
        sendButtonContainer.getElement().setId("sendButtonContainer");
        sendButtonContainer.add(sendButton);

        final FlowPanel valuesContainer = new FlowPanel();
        valuesContainer.getElement().setId("valuesContainer");
        valuesContainer.add(valueLabel);

        final FlowPanel valueMapContainer = new FlowPanel();
        valueMapContainer.getElement().setId("valueMapContainer");
        valueMapContainer.add(valueHtml);

        // Add the controls to this container
        add(multivalueFieldContainer);
        add(sendButtonContainer);
        add(new HTML("<p>"));
        add(valuesContainer);
        add(new HTML("</p>"));

        multivalueField.setFocus(true);
    }
}
