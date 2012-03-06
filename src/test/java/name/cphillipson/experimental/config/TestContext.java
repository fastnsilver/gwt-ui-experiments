package name.cphillipson.experimental.config;

import name.cphillipson.experimental.gwt.server.config.ComponentConfig;
import name.cphillipson.experimental.gwt.server.config.GwtServiceConfig;
import name.cphillipson.experimental.gwt.server.i18n.MessageHelper;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;


@Configuration
@Import(value={ GwtServiceConfig.class, ComponentConfig.class })
@PropertySource(value = { "classpath:/${env:exp}.properties" })
public class TestContext {
    // context used for unit testing service implementations (in experimental)

    @Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(UiMessages.PATH_TO);
        return messageSource;
    }

    @Bean
    public MessageHelper messageHelper() {
        return new MessageHelper(messageSource());
    }
}
