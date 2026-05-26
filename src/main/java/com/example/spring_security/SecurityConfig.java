package com.example.spring_security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/user").hasRole("USER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {

        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("1234"))
                .roles("USER")
                .build();

        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User
                .withUsername("user2")
                .password(passwordEncoder().encode("user2@123"))
                .roles("USER")
                .build();

        UserDetails admin2 = User
                .withUsername("admin2")
                .password(passwordEncoder().encode("admin2@123"))
                .roles("ADMIN")
                .build();

        JdbcUserDetailsManager userDetailsManager =
                new JdbcUserDetailsManager(dataSource);

        if (userDetailsManager.userExists("user")) {
            userDetailsManager.updateUser(user);
        }

        if (userDetailsManager.userExists("admin")) {
            userDetailsManager.updateUser(admin);
        }

        if (userDetailsManager.userExists("user2")) {
            userDetailsManager.updateUser(user2);
        }
        if (userDetailsManager.userExists("admin2")) {
            userDetailsManager.updateUser(admin2);
        }
        return userDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}