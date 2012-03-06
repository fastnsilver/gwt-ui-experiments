package name.cphillipson.experimental.gwt.server.nav;


import name.cphillipson.experimental.gwt.server.i18n.MessageHelper;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;

public interface NavigationConstructor {
    public NavigationInfo constructNavInfo();
    void setMessageHelper(MessageHelper messageHelper);
}
