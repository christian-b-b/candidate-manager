package com.seek.candidatemanager.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.seek.candidatemanager.constants.CandidateManagerConstants;
import com.seek.candidatemanager.errorhandler.CandidateManagerError;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class CandidateManagerAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException {
    CandidateManagerError candidateManagerError = CandidateManagerError.builder()
        .httpStatus(HttpStatus.FORBIDDEN)
        .message("JWT is not valid")
        .code(CandidateManagerConstants.PREFIX_CLIENT_ERROR + CandidateManagerConstants.FORBIDDEN)
        .build();
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    OutputStream outputStream = response.getOutputStream();
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.writeValue(outputStream, candidateManagerError);
    outputStream.flush();
  }

}
