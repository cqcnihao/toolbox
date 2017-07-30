package com.git.toolbox.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.git.toolbox.converter.GeneralByteEnumConverterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
//@EnableWebMvc //使用此注解,WebMvcAutoConfiguration中的配置将不会生效
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);

    @Value("${token.interceptor.exclude.url}")
    private String[] excludePathPatterns;

    //添加IP拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.debug("添加IP拦截器.");
        registry.addInterceptor(new HandlerInterceptorAdapter() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                     Object handler) throws Exception {
//                String clientIp = RequestUtil.getRemoteHost(request);
//                logger.info("客户IP({})对{}的{}请求.", clientIp, RequestUtil.getRequestUri(request), request.getMethod());
                return true;
            }
        }).addPathPatterns("/**").excludePathPatterns("/favicon.ico");

    }


    //自定义json返回的转换器,null值不返回
    @Bean
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        jsonConverter.setObjectMapper(objectMapper);
        jsonConverter.setPrettyPrint(true);
        return jsonConverter;
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        converters.add(customJackson2HttpMessageConverter());

        super.configureMessageConverters(converters);
    }


    @Override
    public void addFormatters(FormatterRegistry registry) {
        //添加枚举类型转换器
        registry.addConverterFactory(new GeneralByteEnumConverterFactory());
    }


}
