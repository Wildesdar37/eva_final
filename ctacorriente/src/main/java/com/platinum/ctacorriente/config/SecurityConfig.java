package com.platinum.ctacorriente.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.platinum.ctacorriente.services.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserDetailService userDetailService;

  public SecurityConfig(UserDetailService userDetailService) {
    this.userDetailService = userDetailService;
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
            .anyRequest().authenticated())
        .formLogin(form -> form
            .loginPage("/login")
            .permitAll()
            .defaultSuccessUrl("/", true)
            .failureUrl("/login?error=true"))
        .logout(logout -> logout
            .permitAll()
            .logoutSuccessUrl("/login?logout=true"))
        .logout(logout -> logout
            .permitAll()
            .logoutSuccessUrl("/login?logout=true"))
        .csrf(csrf -> csrf.disable())
        .userDetailsService(userDetailService);

    return http.build();
  }

}