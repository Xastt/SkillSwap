package ru.xast.SkillSwap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/persInf", "/profInf").permitAll()
                                .anyRequest().authenticated())
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("process_login")
                .defaultSuccessUrl("/persInf", true)
                .failureUrl("/auth/login?error")
    }*/

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}