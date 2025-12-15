package com.karthikeyan2527.comic_reader_backend;

import com.karthikeyan2527.comic_reader_backend.service.ComicUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true) // TODO: Turn debug off
public class SecurityConfiguration {

    @Value("${web.cors.allowed-origins}")
    String[] allowedCorsOrigins;

    @Autowired
    ComicUserDetailsService comicUserDetailsService;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{ // TODO: Move allowed endpoints to application file
        http.authorizeHttpRequests(auth->
                auth.requestMatchers("/chapter/**", "/comic/**", "/links/**", "/user/**").permitAll() // TODO: JWT Auth to */comment
                    .anyRequest().denyAll())
            .csrf(csrf->csrf.disable());

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(comicUserDetailsService)
                .passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

   @Bean
    WebMvcConfigurer addCors(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) { // TODO: Check why nonnull
                registry.addMapping("/**")
                        .allowedOrigins(allowedCorsOrigins)
                        .allowedMethods("GET", "POST");
            }
        };
   }
}