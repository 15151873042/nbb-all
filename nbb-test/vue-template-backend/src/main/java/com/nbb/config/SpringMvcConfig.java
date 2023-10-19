package com.nbb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nbb.framework.jackson.LongToStringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Iterator;
import java.util.List;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 删除原有的AbstractJackson2HttpMessageConverter
        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> messageConverter = iterator.next();
            if (messageConverter instanceof AbstractJackson2HttpMessageConverter) {
                iterator.remove();
            }
        }

        // 将超过16位的long，Long类型转换成String，已避免前端精度丢失
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, new LongToStringSerializer());
        simpleModule.addSerializer(Long.TYPE, new LongToStringSerializer());
        ObjectMapper httpMessageObjectMapper = objectMapper.copy();
        httpMessageObjectMapper.registerModule(simpleModule);

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(httpMessageObjectMapper);
        converters.add(mappingJackson2HttpMessageConverter);
    }

}
