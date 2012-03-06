package name.cphillipson.experimental.gwt.client.module.main.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import name.cphillipson.experimental.gwt.shared.bean.BannerInfo;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

/**
 * The asynchronous counterpart to <code>HeaderService</code>.
 * @author cphillipson
 */
public interface HeaderServiceAsync {
    void getBannerInfo(AsyncCallback<BannerInfo> callback);
    void getTopNavInfo(AsyncCallback<NavigationInfo> callback);
}
