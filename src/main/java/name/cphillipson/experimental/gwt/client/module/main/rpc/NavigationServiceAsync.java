package name.cphillipson.experimental.gwt.client.module.main.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import name.cphillipson.experimental.gwt.shared.bean.NavPerspective;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

/**
 * The asynchronous counterpart to <code>NavigationService</code>.
 * @author cphillipson
 */
public interface NavigationServiceAsync {
    void getNavInfo(NavPerspective perspective, AsyncCallback<NavigationInfo> callback);
}
