package com.portfolio.astrology.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                        .authorizeHttpRequests()
                        .antMatchers(HttpMethod.POST, "/astrology/v1/user/singn-up", "/astrology/v1/user/login")
                        .permitAll()
                        .anyRequest().authenticated().and().cors();

//        http
//                .authorizeHttpRequests((authz) -> authz
//                        .antMatchers(HttpMethod.POST,"/astrology/v1/user/singn-up").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults());
        http.addFilterBefore(new SecurityFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
