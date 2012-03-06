package name.cphillipson.experimental.gwt.server.config;

import java.util.List;

import name.cphillipson.experimental.gwt.server.i18n.MessageHelper;
import name.cphillipson.experimental.gwt.server.interceptor.DateTimeZoneHandlerInterceptor;
import name.cphillipson.experimental.gwt.shared.i18n.UiMessages;

import org.joda.time.DateTimeZone;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.format.datetime.joda.JodaTimeContextHolder;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC Configuration.
 * Implements {@link WebMvcConfigurer}, which provides convenient callbacks that allow us to customize aspects of the Spring Web MVC framework.
 * These callbacks allow us to register custom interceptors, message converters, argument resolvers, resource handling, and other things.
 * @author Chris Phillipson
 * @see WebMvcConfigurer
 */
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    // implementing WebMvcConfigurer

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new AccountExposingHandlerInterceptor());
        registry.addInterceptor(new DateTimeZoneHandlerInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //argumentResolvers.add(new AccountHandlerMethodArgumentResolver());
        argumentResolvers.add(new DateTimeZoneHandlerMethodArgumentResolver());
    }

    // serve static resources

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // i.e., images, JS, and CSS
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        // i.e., GWT module code-generated resources
        registry.addResourceHandler("/*").addResourceLocations("/");
        registry.addResourceHandler("/EMKT/**").addResourceLocations("/EMKT/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJacksonHttpMessageConverter());
    }


    // additional webmvc-related beans

    /**
     * Messages to support internationalization/localization.
     */
    @Bean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(UiMessages.PATH_TO);
        return messageSource;
    }

    @Bean
    public MessageHelper messageHelper() {
        final MessageHelper helper = new MessageHelper(messageSource());
        return helper;
    }

    /**
     * Supports FileUploads.
     */
    @Bean
    public MultipartResolver multipartResolver() {
        final CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(500000);
        return multipartResolver;
    }

    // custom argument resolver inner classes

    // TODO Security -- authentication and authorization
    /*
    private static class AccountHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return MuiUser.class.isAssignableFrom(parameter.getParameterType());
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest,
                WebDataBinderFactory binderFactory) throws Exception {
            final Authentication auth = (Authentication) webRequest.getUserPrincipal();
            return auth != null && auth.getPrincipal() instanceof User ? auth.getPrincipal() : null;
        }

    }
     */

    private static class DateTimeZoneHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return DateTimeZone.class.isAssignableFrom(parameter.getParameterType());
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest webRequest,
                WebDataBinderFactory binderFactory) throws Exception {
            return JodaTimeContextHolder.getJodaTimeContext().getTimeZone();
        }

    }

}