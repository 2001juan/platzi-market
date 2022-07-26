package com.platzi.market.web.security;


import com.platzi.market.domain.service.PlatziUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;

public class SecurityConfig implements WebSecurityConfigurer {

    @Autowired
    private PlatziUserDetailsService platziUserDetailsService;
    @Override
    public void init(SecurityBuilder builder) throws Exception {

    }

    @Override
    public void configure(SecurityBuilder builder) throws Exception {
        ((AuthenticationManagerBuilder) builder).userDetailsService(platziUserDetailsService);
    }
}
