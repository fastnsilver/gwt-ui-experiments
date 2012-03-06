package name.cphillipson.experimental.gwt.server.dao;

import javax.inject.Inject;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import name.cphillipson.experimental.config.TestContext;

@ContextConfiguration(classes={ TestContext.class }, loader=AnnotationConfigContextLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDataTest {

    @Inject
    private TestData data;

    @Test
    public void testGetEnergyOffers() {
        Assert.assertNotNull(data.getEnergyOffers());
    }

    @Test
    public void testGetRegUpOffers() {
        Assert.assertNotNull(data.getRegUpOffers());
    }

}
