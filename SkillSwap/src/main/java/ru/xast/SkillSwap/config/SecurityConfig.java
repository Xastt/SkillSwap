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
import ru.xast.SkillSwap.security.CustomAuthSuccessHandler;
import ru.xast.SkillSwap.services.UsersDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final UsersDetailsService usersDetailsService;
    private final CustomAuthSuccessHandler customAuthSuccessHandler;

    public SecurityConfig(UsersDetailsService usersDetailsService, CustomAuthSuccessHandler customAuthSuccessHandler) {
        this.usersDetailsService = usersDetailsService;
        this.customAuthSuccessHandler = customAuthSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests ->
                        authorizeHttpRequests
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/auth/login","auth/registration","/auth/welcome", "/css/**").permitAll()
                                .anyRequest().hasAnyRole("USER","ADMIN")
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .successHandler(customAuthSuccessHandler)
                        .failureUrl("/auth/login?error")
                )
                .build();
    }

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