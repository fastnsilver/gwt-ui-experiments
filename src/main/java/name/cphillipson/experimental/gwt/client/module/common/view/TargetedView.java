package name.cphillipson.experimental.gwt.client.module.common.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.view.LazyView;
import name.cphillipson.experimental.gwt.client.module.common.bean.NavTarget;

/**
 * A view that is to be targeted by a presenter with an "on*" prefixed method.  A destination of the CommonTokenService and typically invoked by
 * a "place" event in an event bus (convention has place event's method name starting with "*").
 * 
 * @param <R> the result the view is responsible for rendering
 * @author cphillipson
 *
 */
public interface TargetedView<R> extends IsWidget, LazyView {
    void prepare(NavTarget target, R result);
}
