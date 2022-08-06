package com.example.tinderclonebackend.config

import com.example.tinderclonebackend.security.filter.FirebaseTokenFilter
import com.example.tinderclonebackend.security.provider.FirebaseAuthenticationProvider
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.sql.Timestamp
import java.util.*
import kotlin.collections.HashMap


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true,)
class SecurityConfig {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var firebaseAuthenticationProvider: FirebaseAuthenticationProvider

    @Bean
    fun restAuthenticationEntryPoint(): AuthenticationEntryPoint? {
        return AuthenticationEntryPoint { _, httpServletResponse, _ ->
            val errorObject: MutableMap<String, Any> = HashMap()
            val errorCode = 401
            errorObject["message"] = "Unauthorized access of protected resource, invalid credentials"
            errorObject["error"] = HttpStatus.UNAUTHORIZED
            errorObject["code"] = errorCode
            errorObject["timestamp"] = Timestamp(Date().time)
            httpServletResponse.contentType = "application/json;charset=UTF-8"
            httpServletResponse.status = errorCode
            httpServletResponse.writer.write(objectMapper.writeValueAsString(errorObject))
        }
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().csrf().disable().httpBasic().and()
            .authorizeRequests().anyRequest().authenticated()
            .and().exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint())
            .and().addFilterBefore(FirebaseTokenFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .authenticationProvider(firebaseAuthenticationProvider)
        return http.build()
    }
}