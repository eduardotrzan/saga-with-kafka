package com.order.payment.generic.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Disable CSRF (cross site request forgery)
        http.csrf()
                .disable();

        // No session will be created or used by spring security
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .anyRequest()
                .permitAll();
    }
}
