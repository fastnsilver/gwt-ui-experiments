package name.cphillipson.experimental.gwt.client.module.common.widget.popup;

import java.util.Set;

import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import name.cphillipson.experimental.gwt.client.module.common.service.TokenService;
import name.cphillipson.experimental.gwt.client.util.NavigableUtil;
import name.cphillipson.experimental.gwt.client.util.UiMessagesHelper;
import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NavOption;
import name.cphillipson.experimental.gwt.shared.bean.Operation;

public class NavNodeMenu extends DecoratedPopupPanel {

    private static final String MAX_WIDTH = "130px";

    private final NavigableUtil util;
    private final TokenService tokenService;

    public NavNodeMenu(NavigableUtil util, TokenService tokenService) {
        super(true); // auto-hide
        this.util = util;
        this.tokenService = tokenService;
    }

    public void setInput(NavNode node) {
        final FlexTable optionsContainer = new FlexTable();
        optionsContainer.setWidth(MAX_WIDTH);

        Set<NavOption> currentOptions;
        int rowIndex = 0;
        for (final Operation op : Operation.orderedValues()) {
            currentOptions = util.getOptions(node, op);
            // XXX op exclusions might be specified elsewhere... anyway we never expect to execute a submit or reset on
            // a form/grid from a tree node
            if (currentOptions != null && !op.equals(Operation.NONE) && !op.equals(Operation.SAVE)
                    && !op.equals(Operation.RESET)) {
                optionsContainer.setWidget(rowIndex, 0, new Label(UiMessagesHelper.getOperation(op)));
                int colIndex = 1;
                for (final NavOption anOption : currentOptions) {
                    setOption(anOption, optionsContainer, rowIndex, colIndex);
                    colIndex++;
                }
                rowIndex++;
            }
        }
        // TODO provide a debug id... this will most likely necessitate generation of a unique key
        if (optionsContainer.getRowCount() > 0) {
            setWidget(optionsContainer);
        }
    }

    private void setOption(NavOption option, FlexTable optionsContainer, int rowIndex, int colIndex) {
        String token;
        Label label;
        token = tokenService.getToken(option);
        if (token.isEmpty()) {
            label = new Label(option.getName());
            optionsContainer.setWidget(rowIndex, colIndex, label);
        } else {
            final Hyperlink link = new Hyperlink(option.getName(), token);
            optionsContainer.setWidget(rowIndex, colIndex, link);
        }
    }

}
