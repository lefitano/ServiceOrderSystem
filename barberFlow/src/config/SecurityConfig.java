package com.barberflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration 
public class SecurityConfig {
    @Bean 
    public PasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
