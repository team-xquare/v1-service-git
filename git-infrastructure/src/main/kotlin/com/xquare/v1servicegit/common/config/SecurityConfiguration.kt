package com.xquare.v1servicegit.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration {
    @Bean
    protected fun filterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http
            .httpBasic().disable()
            .formLogin().disable()
            .csrf().disable()
            .cors().disable()
            .authorizeExchange()
            .pathMatchers(HttpMethod.POST, "/gits").permitAll()
            .pathMatchers(HttpMethod.GET, "/gits").permitAll()
            .pathMatchers(HttpMethod.GET, "/gits/all").permitAll()
            .pathMatchers(HttpMethod.GET, "/gits/exist").permitAll()
            .pathMatchers(HttpMethod.PATCH, "/gits").permitAll()
            .anyExchange().authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint(CustomAuthenticationEntryPoint())
            .and()
            .build()
}

@Component
class CustomAuthenticationEntryPoint : ServerAuthenticationEntryPoint {
    override fun commence(exchange: ServerWebExchange, ex: AuthenticationException): Mono<Void> {
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        return exchange.response.setComplete()
    }
}
