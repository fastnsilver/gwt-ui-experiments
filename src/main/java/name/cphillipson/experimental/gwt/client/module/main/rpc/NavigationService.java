package name.cphillipson.experimental.gwt.client.module.main.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import name.cphillipson.experimental.gwt.shared.Endpoints;
import name.cphillipson.experimental.gwt.shared.bean.NavPerspective;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

@RemoteServiceRelativePath(Endpoints.CLIENT_GWT_PREFIX + Endpoints.GET_NAVIGATION)
public interface NavigationService extends RemoteService {

    NavigationInfo getNavInfo(NavPerspective perspective);

}
