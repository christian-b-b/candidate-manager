package com.seek.candidatemanager.config;

import com.seek.candidatemanager.constants.CandidateManagerConstants;
import com.seek.candidatemanager.security.CandidateManagerAccessDeniedHandler;
import com.seek.candidatemanager.security.CandidateManagerAuthenticationEntryPoint;
import com.seek.candidatemanager.security.JwtTokenFilter;
import com.seek.candidatemanager.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class CandidateManagerSecurityConfig {

  AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserDetailsService userDetailsService;

  private final String API_LOGIN = CandidateManagerConstants.API_VERSION +
          CandidateManagerConstants.RESOURCE_USERS + CandidateManagerConstants.RESOURCE_LOGIN;

  private final String API_CANDIDATE = CandidateManagerConstants.API_VERSION +
          CandidateManagerConstants.RESOURCE_CANDIDATES;





  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
        AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsService);
    authenticationManager = authenticationManagerBuilder.build();

    http.csrf().disable().cors().disable().authorizeHttpRequests()
        .antMatchers(API_LOGIN).permitAll()
        .antMatchers(API_CANDIDATE).hasAuthority(CandidateManagerConstants.ADMIN_ROLE)
        .anyRequest().authenticated()
        .and()
        .authenticationManager(authenticationManager)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.exceptionHandling().accessDeniedHandler(accessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint());
    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint(){
    return new CandidateManagerAuthenticationEntryPoint();
  }

  @Bean
  AccessDeniedHandler accessDeniedHandler(){
    return new CandidateManagerAccessDeniedHandler();
  }

  @Bean
  public JwtTokenFilter authenticationJwtTokenFilter() {
    return new JwtTokenFilter(jwtTokenProvider);
  }
}
