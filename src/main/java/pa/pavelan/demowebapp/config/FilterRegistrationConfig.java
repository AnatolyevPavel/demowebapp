package pa.pavelan.demowebapp.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import pa.pavelan.webmetrics.handler.MetricsFilter;

@Component
public class FilterRegistrationConfig {
    @Bean
    public FilterRegistrationBean<MetricsFilter> filterRegistration () {
        FilterRegistrationBean<MetricsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new MetricsFilter());
        registration.setOrder(1);
        return registration;
    }
}
