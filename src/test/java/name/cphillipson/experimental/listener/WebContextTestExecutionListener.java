package name.cphillipson.experimental.listener;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>This will make the request scope available to unit and integration tests.<br/>
 * Refer to <a href="http://forum.springsource.org/showthread.php?73831-junit-test-quot-no-scope-registered-for-scope-session-quot">Spring Forums (post #73831)</a>
 * and <a href="http://forum.springsource.org/showthread.php?50722-Integration-tests-with-mocked-session/page2">Spring Forums (post #50722)</a>.</p>
 * 
 * @author: cphillipson
 *
 */
public class WebContextTestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {

        if (testContext.getApplicationContext() instanceof GenericApplicationContext) {
            final GenericApplicationContext context = (GenericApplicationContext) testContext.getApplicationContext();
            final ConfigurableListableBeanFactory beanFactory = context
                    .getBeanFactory();
            final Scope requestScope = new SimpleThreadScope();
            beanFactory.registerScope("request", requestScope);
            final MockHttpServletRequest request = new MockHttpServletRequest();
            final MockHttpSession session = new MockHttpSession();
            request.setSession(session);
            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        }
    }

}