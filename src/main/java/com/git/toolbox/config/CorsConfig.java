package com.git.toolbox.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.stream.Stream;

/**
 * 处理跨域问题
 *
 */
@Configuration
public class CorsConfig {
	
	@Value("${cors.allowed.origin}")
	String allowCorsOrigin;
	
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        if(StringUtils.isBlank(allowCorsOrigin)){
        	return corsConfiguration;
        }
        Stream.of(allowCorsOrigin.split(",")).forEach(s-> corsConfiguration.addAllowedOrigin(s));//1       
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL); // 2允许所有请求头
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL); // 3 允许所有请求方法
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }
}