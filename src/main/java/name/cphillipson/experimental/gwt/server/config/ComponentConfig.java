package name.cphillipson.experimental.gwt.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import name.cphillipson.experimental.gwt.server.controller.CPM;

/**
 * Configuration for this application: @Components such as @Services, @Repositories, and @Controllers.
 * Loads externalized property values required to configure the various application properties.
 * Not much else here, as we rely on @Component scanning in conjunction with @Inject by-type autowiring.
 * @author Chris Phillipson
 */
@Configuration
@ComponentScan(basePackageClasses = {CPM.class} )
public class ComponentConfig {

    /**
     * Properties to support the 'standard' mode of operation.
     */
    @Configuration
    @Profile("standard")
    @PropertySource(value = { "classpath:/${env:exp}.properties" })
    static class Standard {
    }
}
