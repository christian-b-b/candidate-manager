package com.seek.candidatemanager.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.seek.candidatemanager.constants.CandidateManagerConstants;
import com.seek.candidatemanager.errorhandler.CandidateManagerError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String token = jwtTokenProvider.resolveToken(request);
    try {
      if (token != null && jwtTokenProvider.validateToken(token)) {
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (Throwable e) {
      SecurityContextHolder.clearContext();
      CandidateManagerError candidateManagerError = CandidateManagerError.builder()
          .httpStatus(HttpStatus.UNAUTHORIZED)
          .message("Expired or invalid JWT token")
          .code(CandidateManagerConstants.PREFIX_CLIENT_ERROR + CandidateManagerConstants.UNAUTHORIZED)
          .build();
      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      OutputStream outputStream = response.getOutputStream();
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      objectMapper.writeValue(outputStream, candidateManagerError);
      outputStream.flush();
      return;
    }
    filterChain.doFilter(request, response);
  }
}
