package name.cphillipson.experimental.gwt.server.i18n;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import name.cphillipson.experimental.gwt.server.http.RequestHelper;

import org.springframework.web.servlet.support.RequestContextUtils;



/**
 * Wraps Spring's {@link RequestContextUtils#getLocale(HttpServletRequest)} in
 * order to obtain the current locale from each request.
 * 
 * @author cphillipson
 * 
 */
public class LocaleHelper {

    /**
     * Obtain the current locale from a request.
     * @return the current locale as determined by a LocaleResolver
     */
    public static Locale getCurrentLocale() {
        return RequestContextUtils.getLocale(RequestHelper.getRequest());
    }


}
