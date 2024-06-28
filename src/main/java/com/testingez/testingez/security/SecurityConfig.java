package com.testingez.testingez.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        // Setup with URLs are available to who
                        authorizeRequests ->
                                authorizeRequests
                                        // all static resources to common locations (css, image, js) are available to everyone
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        // some more resources for all users
                                        .requestMatchers("/", "/account/login", "/account/create").permitAll()
                                        // all other URLs should be authenticated
                                        .anyRequest().authenticated()
                )
                .formLogin(
                        formLogin -> {
                            formLogin
                                    // Where is our custom login form
                                    .loginPage("/account/login")
                                    // What is the name of the username parameter in the login POST request
                                    .usernameParameter("username")
                                    // What is the name of the password parameter in the login POST request
                                    .passwordParameter("password")
                                    // What will happen if login is successful
                                    .defaultSuccessUrl("/user/home")
                                    // What will happen if login is unsuccessful
                                    .failureUrl("/account/login?error");
                        }
                )
                .logout(
                        logout -> {
                            logout
                                    // What is the logout URL
                                    .logoutUrl("/account/logout")
                                    // Where should we go after logout
                                    .logoutSuccessUrl("/")
                                    // Invalidate session after logout
                                    .invalidateHttpSession(true);
                        }
                )
                .build();
    }

}
