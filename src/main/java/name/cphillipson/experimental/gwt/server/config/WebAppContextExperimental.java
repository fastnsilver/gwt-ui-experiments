package name.cphillipson.experimental.gwt.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value={ GwtServiceConfig.class, ComponentConfig.class, MvcConfig.class })
public class WebAppContextExperimental {
    // context used for web application

}
