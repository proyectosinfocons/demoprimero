package com.example.demoprimero.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserDetailsService) // Utiliza CustomUserDetailsService para la autenticación
                .passwordEncoder(passwordEncoder()); // Usa BCryptPasswordEncoder para la codificación de contraseñas
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desactivar CSRF para el uso de la consola H2 y APIs
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll() // Permitir todos los accesos a la consola H2
                .antMatchers(HttpMethod.POST, "/api/customers/").permitAll() // Permitir crear usuarios sin autenticación
                .antMatchers(HttpMethod.GET, "/api/customers/").permitAll() // Permitir listar usuarios sin autenticación
                .antMatchers("/api/posts/**").authenticated()
                .anyRequest().authenticated() // Todos los demás endpoints requieren autenticación
                .and()
                .formLogin().permitAll() // Opcional: si quieres permitir login con form
                .and()
                .httpBasic() // Autenticación básica
                .and()
                .headers().frameOptions().disable(); // Desactivar opciones de frame para permitir el uso de H2 Console
    }
}
