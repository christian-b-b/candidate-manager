package com.seek.candidatemanager.security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.seek.candidatemanager.constants.CandidateManagerConstants;
import com.seek.candidatemanager.errorhandler.CandidateManagerError;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class CandidateManagerAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
                       AuthenticationException authException) throws IOException {
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
  }

}
