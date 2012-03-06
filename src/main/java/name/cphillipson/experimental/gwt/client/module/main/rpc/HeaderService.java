package name.cphillipson.experimental.gwt.client.module.main.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import name.cphillipson.experimental.gwt.shared.Endpoints;
import name.cphillipson.experimental.gwt.shared.bean.BannerInfo;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

@RemoteServiceRelativePath(Endpoints.CLIENT_GWT_PREFIX + Endpoints.GET_HEADER)
public interface HeaderService extends RemoteService {
    public BannerInfo getBannerInfo();
    public NavigationInfo getTopNavInfo();
}
