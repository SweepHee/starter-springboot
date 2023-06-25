package com.example.boardpractice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable().authorizeHttpRequests()
                .requestMatchers("/user/**").authenticated()
//                .requestMatchers("/board/**").authenticated()
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
//                .usernameParameter("id")
                .loginPage("/login")
                .loginProcessingUrl("/auth/login/process")
                .defaultSuccessUrl("/")

                .and()
                .logout()
                .logoutUrl("/logout")

                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
        ;

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().anyRequest().requestMatchers("/static/**", "/webjars/**");
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}