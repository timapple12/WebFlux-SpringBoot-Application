package com.example.webFlux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.net.http.HttpHeaders;

/**
 * Class where configuring Spring Security
 * */

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    private final AuthManager authManager;
    private final SecurityContextRepository repository;

    public WebSecurityConfig(AuthManager authManager, SecurityContextRepository repository) {
        this.authManager = authManager;
        this.repository = repository;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();       /** DEPRECATED use in future encoding for MySQL like MD5 */
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity){

        return serverHttpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(
                        (s, e) ->
                                Mono.fromRunnable(
                                        ()-> s.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                                )
                )
                .accessDeniedHandler(
                        (s, e) ->
                                Mono.fromRunnable(
                                        () -> s.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                                )
                ).and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authManager)
                .securityContextRepository(repository)
                .authorizeExchange()
                .pathMatchers("/", "/login", "/favicon.ico").permitAll()
                .pathMatchers("/main").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .build();
    }



}
