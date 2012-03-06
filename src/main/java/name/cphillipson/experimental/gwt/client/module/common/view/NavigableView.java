package name.cphillipson.experimental.gwt.client.module.common.view;

import com.google.gwt.user.client.ui.IsWidget;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

/**
 * A view designed to provide navigation facilities.
 * @author cphillipson
 *
 */
public interface NavigableView extends IsWidget {
    void prepare(NavigationInfo info);
}
