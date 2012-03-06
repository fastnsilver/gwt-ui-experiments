package name.cphillipson.experimental.gwt.server.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Base class for server-side implementations of GWT RPC services.
 * Lifted from a comment made by tehdanish@gmail.com on <a href="http://code.google.com/p/google-web-toolkit-incubator/wiki/IntegratingWithSpring">IntegratingWithSpring</a>.
 * @author cphillipson
 *
 */
public abstract class BaseRemoteService extends RemoteServiceServlet implements ServletContextAware {

    private ServletContext servletContext;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        doPost(request, response);
        return null; // response handled by GWT RPC over XmlHttpRequest
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public ServletContext getServletContext() {
        return servletContext;
    }
}
