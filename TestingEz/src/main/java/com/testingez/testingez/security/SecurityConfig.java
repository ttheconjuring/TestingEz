package com.testingez.testingez.security;

import com.testingez.testingez.repositories.UserRepository;
import com.testingez.testingez.security.utils.CustomAuthenticationFailureHandler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(
                        // Setup with URLs are available to who
                        authorizeHttpRequests ->
                                authorizeHttpRequests
                                        // all static resources to common locations (css, image, js) are available to everyone
                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                        // some more resources for all users
                                        .requestMatchers("/", "/features", "/FAQs",
                                                "/account/login", "/account/create").permitAll()
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
                                    .defaultSuccessUrl("/user/home", true)
                                    // What will happen if login is unsuccessful
                                    .failureHandler(authenticationFailureHandler());
                        }
                )
                .logout(
                        logout -> {
                            logout
                                    // What is the logout URL
                                    .logoutUrl("/account/logout")
                                    // Where should we go after logout
                                    .logoutSuccessUrl("/")
                                    // Delete the session id cookie
                                    .deleteCookies("JSESSIONID")
                                    // Invalidate session after logout
                                    .invalidateHttpSession(true);
                        }
                )
                // Disable CSRF protection for the upload-avatar endpoint (Optional)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/upload-avatar"))
                )
                .build();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
