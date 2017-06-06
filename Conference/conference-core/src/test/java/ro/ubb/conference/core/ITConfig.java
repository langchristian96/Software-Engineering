package ro.ubb.conference.core;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by langchristian96 on 5/30/2017.
 */


@Configuration
@ComponentScan(value = "ro.ubb.conference.core",
        excludeFilters = {@ComponentScan.Filter(value = {JPAConfig.class}, type = FilterType.ASSIGNABLE_TYPE)})
@Import({JPAConfigIT.class})
@PropertySources({@PropertySource(value = "classpath:db-h2.properties")})
public class ITConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
