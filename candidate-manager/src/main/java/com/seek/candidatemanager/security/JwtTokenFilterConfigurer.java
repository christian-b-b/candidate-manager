package com.seek.candidatemanager.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtTokenFilterConfigurer extends
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  final JwtTokenProvider jwtTokenProvider;

  @Override
  public void configure(HttpSecurity builder) {
    JwtTokenFilter tokenFilter = new JwtTokenFilter(jwtTokenProvider);
    builder.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
