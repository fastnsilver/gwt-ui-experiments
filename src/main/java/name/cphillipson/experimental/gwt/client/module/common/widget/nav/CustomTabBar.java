package name.cphillipson.experimental.gwt.client.module.common.widget.nav;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import name.cphillipson.experimental.gwt.client.module.common.service.TokenService;
import name.cphillipson.experimental.gwt.client.module.main.MainEventBus;
import name.cphillipson.experimental.gwt.client.resources.UiResources;
import name.cphillipson.experimental.gwt.shared.bean.NavOption;

/**
 * Renders a list of <code>NavOption</code>s in horizontal fashion as a set of tabs.
 * Works with a <code>TokenService</code> to generate <a href="http://css-tricks.com/better-tabs-with-round-out-borders/">custom markup</a> for each option.
 * @author cphillipson
 *
 */
public class CustomTabBar extends FlowPanel {

    interface TabTemplate extends SafeHtmlTemplates {
        @Template("<li><a href=\"{0}\">{1}</a></li>")
        SafeHtml tab(String anchorHref, String anchorText);

        @Template("<li class=\"selected\"><a href=\"{0}\">{1}</a></li>")
        SafeHtml currently_selected_tab(String anchorHtml, String anchorText);
    }

    private static TabTemplate template;

    public CustomTabBar() {
        super();
        if (template == null) {
            template = GWT.create(TabTemplate.class);
        }
    }

    public <E extends MainEventBus> void setInput(Collection<NavOption> options, final TokenService tokenService, final E eventBus) {
        this.clear();

        if (options == null) {
            return;
        }

        final Set<Anchor> destinations = new LinkedHashSet<Anchor>();
        final String linkPrefix = Window.Location.getQueryString() + "#";
        String href;
        for (final NavOption option: options) {
            final String token = tokenService.getToken(option);
            href = linkPrefix + token;
            // <a>
            final Anchor to = new Anchor(option.getName(), href);
            destinations.add(to);
        }

        final StringBuilder sb = new StringBuilder();
        sb.append("<ul class=\"").append(UiResources.INSTANCE.style().tabrow()).append("\">");
        // FIXME configure to set the selected tab; for now, always set the first tab as selected
        int idx = 0;
        for (final Anchor a: destinations) {
            if (idx == 0) {
                sb.append(template.currently_selected_tab(a.getHref(), a.getText()).asString());
            } else {
                sb.append(template.tab(a.getHref(), a.getText()).asString());
            }
            idx++;
        }
        sb.append("</ul>");

        this.add(new HTML(sb.toString()));
    }


}
