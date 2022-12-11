package com.portfolio.astrology.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


    @Autowired
    TokenService tokenService;

    @Bean
    protected PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() //CSRF é uma segurança para hackers, autenticaçao via token ja esta protegida
                        .authorizeHttpRequests()
                        .antMatchers(HttpMethod.POST, "/astrology/v1/user/singn-up", "/astrology/v1/user/login")
                        .permitAll()
                        .anyRequest().authenticated().and().cors() //o cors permite que eu acesse a api por requisiçoes externas
                        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Spring nao deve criar uma sessao
                        .and().addFilterBefore(new SecurityFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


/*
    Configuracoes de Autenticacao: AuthenticationManagerBuilder
    Configuracoes de Autorizacao: HttpSecurity http
    Configuracoes de recurdos estaticos (is, css, imagens, etc.): WebSecurity web
*/

}
