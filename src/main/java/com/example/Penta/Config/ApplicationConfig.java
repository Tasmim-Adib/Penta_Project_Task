package com.example.Penta.Config;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.Service.DesignPattern.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final EMSUserRepository repository;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found !!"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        // fetch user details
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public EMSUserFactory emsUserFactory(){
        return new EMSUserFactoryImpl();
    }

    @Bean
    public StudentFactory studentFactory(){
        return new StudentFactoryImpl();
    }

    @Bean
    public TeacherFactory teacherFactory(){
        return new TeacherFactoryImpl();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
