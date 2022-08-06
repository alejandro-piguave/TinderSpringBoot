package com.example.tinderclonebackend.config

import org.springframework.context.annotation.Primary
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JacksonConfig {

    @Primary
    @Bean
    fun jacksonObjectMapper(): ObjectMapper {
        return Jackson2ObjectMapperBuilder().build()
    }
}