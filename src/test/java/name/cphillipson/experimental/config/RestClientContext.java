package name.cphillipson.experimental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource(value = { "classpath:/${env:exp}.properties" })
public class RestClientContext {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
