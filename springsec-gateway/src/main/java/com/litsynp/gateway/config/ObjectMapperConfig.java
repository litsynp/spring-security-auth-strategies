package com.litsynp.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfig {

    @Bean
    @Primary // Override default ObjectMapper
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Locate and register modules that are by default provided JDK ServiceLoader
        objectMapper.findAndRegisterModules();

        // Pretty-format JSON using indents
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // Configure so that dates are in string format, not array format
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // Register modules
        // JavaTimeModule allows serialization/deserialization of time
        objectMapper.registerModules(new JavaTimeModule());

        return objectMapper;
    }
}
