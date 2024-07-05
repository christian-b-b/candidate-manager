package com.seek.candidatemanager.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.seek.candidatemanager.domains.Role;
import com.seek.candidatemanager.errorhandler.CandidateManagerGenericClientException;
import com.seek.candidatemanager.errorhandler.CandidateManagerGenericServerException;
import com.seek.candidatemanager.services.CandidateManagerDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.token.time}")
  private Long tokenTime;

  final private ObjectMapper objectMapper;

  @Autowired
  private CandidateManagerDetailService candidateManagerDetailService;

  public String createToken(Object src, List<Role> roles) {
    try {
      Claims claims = Jwts.claims().setSubject(objectMapper.writeValueAsString(src));
      claims.put("auth", roles.stream()
          .map(role -> new SimpleGrantedAuthority(role.getCode()))
          .filter(Objects::nonNull).collect(Collectors.toList()));
      Date date = new Date();
      Date expiredDate = new Date(date.getTime() + tokenTime);
      return Jwts.builder()
          .setHeaderParam("type", "jwt")
          .setClaims(claims)
          .setIssuedAt(date)
          .setExpiration(expiredDate)
          .signWith(SignatureAlgorithm.HS256, secretKey)
          .compact();
    } catch (JsonProcessingException exception) {
      throw new CandidateManagerGenericServerException("Error during parse text for JWT");
    }
  }

  public String resolveToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (token != null && token.startsWith("Bearer ")) {
      return token.substring(7);
    }
    return null;
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      throw new CandidateManagerGenericClientException("Expired or invalid JWT token",
          HttpStatus.UNAUTHORIZED, null,null);
    }
  }

  public String getUsername(String token) throws JsonProcessingException {
    String payload = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
        .getSubject();
    JwtPayload jwtPayload = objectMapper.readValue(payload, JwtPayload.class);
    return jwtPayload.getUsername();
  }

  public Authentication getAuthentication(String token) throws JsonProcessingException {
    UserDetails userDetails = candidateManagerDetailService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "",
        userDetails.getAuthorities());
  }

}
