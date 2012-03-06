package name.cphillipson.experimental.gwt.client.module.main.view.impl;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import name.cphillipson.experimental.gwt.client.module.main.view.IMessagesView;
import name.cphillipson.experimental.gwt.client.module.main.view.IMessagesView.IMessagesPresenter;
import name.cphillipson.experimental.gwt.client.module.main.view.ReverseCompositeView;
import name.cphillipson.experimental.gwt.shared.bean.MessageInfo;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

@Singleton
public class MessagesView extends ReverseCompositeView<IMessagesPresenter> implements IMessagesView {

    // TODO Messages should be rendered within a dialog box

    @UiTemplate("MessagesView.ui.xml")
    interface MessagesViewUiBinder extends UiBinder<Widget, MessagesView> { }
    private static MessagesViewUiBinder uiBinder = GWT.create(MessagesViewUiBinder.class);

    @UiField
    FlexTable messagesTable;

    public MessagesView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void prepare(List<MessageInfo> messages) {
        // XXX Consider using ReadOnlyGrid here?
        messagesTable.setWidth("100%");
        messagesTable.getRowFormatter().addStyleName(0, "tableHeader");

        // headers
        messagesTable.setText(0, 0, UiMessages.INSTANCE.id());
        messagesTable.setText(0, 1, UiMessages.INSTANCE.priority());
        messagesTable.setText(0, 2, UiMessages.INSTANCE.text());

        int i = 1;
        for (final MessageInfo m: messages) {
            messagesTable.setText(i, 0, m.getId());
            messagesTable.setText(i, 1, m.getPriority());
            messagesTable.setText(i, 2, m.getText());
            i++;
        }
    }

}
