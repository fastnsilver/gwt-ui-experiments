package name.cphillipson.experimental.gwt.client.module.common.widget.nav;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import name.cphillipson.experimental.gwt.client.resources.UiResources;

/**
 * Renders a trail of "breadcrumbs".  Mirrors hierarchy of currently selected item in {@link NavPanel}.
 * @author cphillipson
 *
 */
public class BreadcrumbTrail extends FlowPanel {

    private static final String DEFAULT_CRUMB_TRAIL_STYLE = UiResources.INSTANCE.style().breadcrumbTrail();
    private static final String DEFAULT_CRUMB_STYLE = UiResources.INSTANCE.style().breadcrumb();
    private static final String DEFAULT_CRUMB_SEPARATOR = "&raquo;";

    private String crumbStyleName;
    private String crumbSeparator;

    public BreadcrumbTrail() {
        super();
        setDefaults();
    }

    private void setDefaults() {
        setCrumbTrailStyleName(DEFAULT_CRUMB_TRAIL_STYLE);
        setCrumbStyleName(DEFAULT_CRUMB_STYLE);
        setCrumbSeparator(DEFAULT_CRUMB_SEPARATOR);
    }

    public void setInput(String... crumbs) {
        this.clear();
        if (crumbs == null) {
            return;
        }

        final StringBuilder crumbTrailBuilder = new StringBuilder();
        crumbTrailBuilder.append(generateStartTag());
        for (final String crumb: crumbs) {
            crumbTrailBuilder.append(generateCrumb(crumb)).append(generateSeparator());
        }
        // remove last separator
        final String rawTrail = crumbTrailBuilder.toString();
        final int lastIndexOfSeparator = rawTrail.lastIndexOf(DEFAULT_CRUMB_SEPARATOR);
        final String crumbTrail = rawTrail.substring(0, lastIndexOfSeparator - 1);

        this.add(new HTML(crumbTrail));
    }

    private String generateStartTag() {
        final StringBuilder sb = new StringBuilder();
        sb.append("<ul class=\"").append(crumbStyleName).append("\">");
        return sb.toString();
    }

    private String generateCrumb(String crumb) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<li class=\"").append(crumbStyleName).append("\">").append(crumb).append("</li>");
        return sb.toString();
    }

    private String generateSeparator() {
        final StringBuilder sb = new StringBuilder();
        sb.append("<li class=\"").append(crumbStyleName).append("\">").append(crumbSeparator).append("</li>");
        return sb.toString();
    }

    private String generateEndTag() {
        return "</ul>";
    }

    public void setCrumbTrailStyleName(String crumbTrailStyleName) {
        setStyleName(crumbTrailStyleName);
    }

    public void setCrumbStyleName(String crumbStyleName) {
        this.crumbStyleName = crumbStyleName;
    }

    public void setCrumbSeparator(String crumbSeparator) {
        this.crumbSeparator = crumbSeparator;
    }
}
