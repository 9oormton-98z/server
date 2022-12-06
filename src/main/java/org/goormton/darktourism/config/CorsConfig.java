package org.goormton.darktourism.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Slf4j
@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        log.debug("CorsFilter가 Spring Filter Chain에 등록됨");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOriginPattern("*");
        config.addExposedHeader("Set-Cookie");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}