package com.lobo.onThisDay.config;

import com.lobo.onThisDay.component.MonthDayConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * Classe de configuração para dependencias do Webservice.
 *
 * @author Gustavo Lobo
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new MonthDayConverter());
    }

}
