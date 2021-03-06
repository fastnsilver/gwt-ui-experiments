package name.cphillipson.experimental.gwt.client.module.common.widget.nav;

import java.util.Collection;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import name.cphillipson.experimental.gwt.client.module.common.service.TokenService;
import name.cphillipson.experimental.gwt.client.resources.UiResources;
import name.cphillipson.experimental.gwt.shared.bean.NavOption;

/**
 * Renders a list of <code>NavOption</code>s in vertical fashion.  Works with a <code>CommonTokenService</code> to
 * generate GWT <code>Hyperlink</code> for each option.
 * @author cphillipson
 *
 */
public class VerticalLinkBar extends FlowPanel {

    private String linkStyleName;

    public VerticalLinkBar() {
        super();
        setDefaults();
    }

    private void setDefaults() {
        linkStyleName = UiResources.INSTANCE.style().v_navItem();
    }

    public void setInput(Collection<NavOption> options, TokenService tokenService) {
        this.clear();
        if (options == null) {
            return;
        }
        Hyperlink to;
        for (final NavOption option: options) {
            to = new Hyperlink(option.getName(), tokenService.getToken(option));
            to.setStyleName(linkStyleName);
            this.add(to);
        }
    }

    public void setLinkStyleName(String linkStyleName) {
        this.linkStyleName = linkStyleName;
    }
}
