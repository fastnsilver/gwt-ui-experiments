package name.cphillipson.experimental.gwt.server.config;

import name.cphillipson.experimental.gwt.server.controller.MarketService;
import name.cphillipson.experimental.gwt.server.controller.stub.StubMarketService;
import name.cphillipson.experimental.gwt.server.dao.TestData;
import name.cphillipson.experimental.gwt.server.nav.NavigationFactory;
import name.cphillipson.experimental.gwt.server.nav.stub.StubNavigationFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GwtServiceConfig {

    @Bean
    public TestData testData() {
        return new TestData();
    }

    @Bean
    public NavigationFactory navigationFactory() {
        return new StubNavigationFactory();
    }


    @Bean
    public MarketService marketService() {
        return new StubMarketService();
    }

}
