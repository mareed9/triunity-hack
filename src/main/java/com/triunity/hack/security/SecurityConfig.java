package com.triunity.hack.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .disable())  // Isključuje CSRF zaštitu za testiranje
                .cors(cors -> cors
                        .disable())  // Isključuje CORS za jednostavno testiranje, uključite ovo prema potrebi
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**", "/triunity/api/auth/register", "/triunity/api/auth/login", "/triunity/api/customers/**", "/triunity/api/mobile-devices/**","/triunity/api/tariff-plans/**","/triunity/api/phones/**","/triunity/api/recommendation/**")
                        .permitAll()  // Omogućava pristup ovim URL rutama bez autentifikacije
                        .anyRequest().authenticated()  // Zahteva autentifikaciju za ostale rute
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));  // Omogućava pristup H2 konzoli u okviru frejma

        return http.build();
    }
}
