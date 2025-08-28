package com.tf.restocompras.config.security;

import com.tf.restocompras.config.jwt.JwtTokenVerifierFilter;
import com.tf.restocompras.config.jwt.JwtUserAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    private final JwtUserAuthenticationFilter jwtUserAuthenticationFilter;
    private final JwtTokenVerifierFilter jwtTokenVerifierFilter;


    public SpringSecurityConfig(JwtUserAuthenticationFilter jwtUserAuthenticationFilter, JwtTokenVerifierFilter jwtTokenVerifierFilter) {
        this.jwtUserAuthenticationFilter = jwtUserAuthenticationFilter;
        this.jwtTokenVerifierFilter = jwtTokenVerifierFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html",
                                        "/webjars/**"
                                ).permitAll());
        http.addFilter(jwtUserAuthenticationFilter);
        http.addFilterAfter(jwtTokenVerifierFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}