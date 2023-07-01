package com.xquare.git.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration {
    @Bean
    protected fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .formLogin().disable()
            .csrf().disable()
            .cors().disable()
            .authorizeExchange()
            .anyExchange().authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint(HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
            .build()
    }
}