package ru.xast.SkillSwap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.xast.SkillSwap.services.UsersDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final UsersDetailsService usersDetailsService;

    public SecurityConfig(UsersDetailsService usersDetailsService) {
        this.usersDetailsService = usersDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/auth/login","auth/registration","/persInf", "/profInf").permitAll()
                                .anyRequest().hasAnyRole("USER","ADMIN")
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/persInf", true)
                        .failureUrl("/auth/login?error")
                )
                .build();
    }

    /*protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }*/

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(usersDetailsService)
                .passwordEncoder(getPasswordEncoder());
        return authenticationManagerBuilder.build();
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}