package com.nbb.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nbb.framework.jackson.LongToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizeObjectMapper() {
        return jacksonObjectMapperBuilder -> {
            // 设置LocalDateTime序列化方式
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));

            /// FIXME 由于这里是全局配置的ObjectMapper，当配置Long转String的时候，redis json序列化的时候由于类型转换会报错，所以以下配置只配置在MappingJackson2HttpMessageConverter
//            // 将超过16位的long，Long类型转换成String，已避免前端精度丢失
//            jacksonObjectMapperBuilder.serializerByType(Long.class, new LongToStringSerializer());
//            jacksonObjectMapperBuilder.serializerByType(Long.TYPE, new LongToStringSerializer());

        };
    }
}
