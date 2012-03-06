package name.cphillipson.experimental.gwt.server;

import javax.inject.Inject;

import name.cphillipson.experimental.config.TestContext;
import name.cphillipson.experimental.gwt.client.module.main.rpc.NavigationService;
import name.cphillipson.experimental.gwt.server.i18n.MessageHelper;
import name.cphillipson.experimental.gwt.shared.bean.NavNode;
import name.cphillipson.experimental.gwt.shared.bean.NavPerspective;
import name.cphillipson.experimental.gwt.shared.bean.NavigationInfo;
import name.cphillipson.experimental.listener.WebContextTestExecutionListener;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, WebContextTestExecutionListener.class})
@ContextConfiguration(classes={ TestContext.class }, loader=AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class StubNavigationServiceTest {

    @Inject
    private NavigationService service;

    @Inject
    private MessageHelper messageHelper;

    @Test
    public void testGetNavInfo() {
        for (final NavPerspective np: NavPerspective.values()) {
            final NavigationInfo info = service.getNavInfo(np);
            for (final NavNode navNode: info.allRootNodes()) {
                Assert.assertNotNull(navNode);
            }
        }
    }

}
