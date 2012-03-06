package name.cphillipson.experimental.gwt.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.ImageResource.RepeatStyle;

/**
 * <a href="http://code.google.com/webtoolkit/doc/latest/DevGuideClientBundle.html">ClientBundle</a> for this application.
 * @author cphillipson
 *
 */
public interface UiResources extends ClientBundle {

    public static final UiResources INSTANCE = GWT.create(UiResources.class);

    public interface Style extends CssResource {
        String about();
        String appTitle();
        String background();
        String banner();
        String bold();
        String breadcrumb();
        String breadcrumbTrail();
        String busy();
        String busyWrapper();
        String center();
        String container();
        String content();
        String error();
        String floatLeft();
        String footer();
        String gridWrapper();
        String gridButtonBar();
        String h_navItem();
        String heading();
        String help();
        String label();
        String leftNavigation();
        String leftPadded();
        String main();
        String marketClosed();
        String marketOpen();
        String menuBorder();
        String messages();
        String noBorder();
        String rightAligned();
        String rightPadded();
        String rightSidebar();
        String selectedNode();
        String shadedRow();
        String tabrow();
        String topContainer();
        String v_navItem();
        String user();
    }

    @Source("busy_indicator.gif")
    ImageResource busy();

    @Source("core.css")
    Style style();

    @Source("background-tiled.jpg")
    @ImageOptions(repeatStyle=RepeatStyle.Both)
    ImageResource tile();

}
