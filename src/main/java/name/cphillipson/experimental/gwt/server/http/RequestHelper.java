package name.cphillipson.experimental.gwt.server.http;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Gets the current <code>HttpServletRequest</code> employing some Spring Web framework classes.
 * 
 * @author cphillipson
 *
 */
public class RequestHelper {

    public static HttpServletRequest getRequest() {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest currentRequest;
        if (HttpServletRequest.class.isAssignableFrom(requestAttributes.getClass())) {
            currentRequest = (HttpServletRequest) requestAttributes;
        } else {
            final ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
            currentRequest = sra.getRequest();
        }
        return currentRequest;
    }
}
