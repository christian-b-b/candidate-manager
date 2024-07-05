package com.seek.candidatemanager.errorhandler;


import com.seek.candidatemanager.constants.CandidateManagerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class CandidateManagerExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(HttpStatusCodeException.class)
  protected ResponseEntity<Object> handleHttpRestClient(HttpStatusCodeException ex) {
    CandidateManagerError candidateManagerError = null;
    if (ex.getStatusCode().is4xxClientError()) {
      candidateManagerError = CandidateManagerError.builder().httpStatus(ex.getStatusCode())
          .code(CandidateManagerConstants.PREFIX_CLIENT_ERROR).build();
    } else if (ex.getStatusCode().is5xxServerError()) {
      candidateManagerError = CandidateManagerError.builder().httpStatus(ex.getStatusCode())
          .code(CandidateManagerConstants.PREFIX_SERVER_ERROR).build();
    }
    candidateManagerError.setMessage(ex.getMessage());
    log.error("Error HTTP Request Client: {}", ex.getMessage());
    return buildResponseEntity(candidateManagerError);
  }

  @ExceptionHandler(CandidateManagerNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(CandidateManagerNotFoundException ex) {
    log.error("Entity Not Found: {}", ex.getMessage());
    if (ex.getCode()!=null){
      return buildResponseEntity(CandidateManagerError.builder().httpStatus(HttpStatus.NOT_FOUND)
              .code(ex.getCode())
              .message(ex.getMessage()).build());

    }else{
      return buildResponseEntity(CandidateManagerError.builder().httpStatus(HttpStatus.NOT_FOUND)
              .code(CandidateManagerConstants.PREFIX_CLIENT_ERROR + CandidateManagerConstants.NOT_FOUND)
              .message(ex.getMessage()).build());
    }

  }

  @ExceptionHandler(CandidateManagerUnauthorizedException.class)
  protected ResponseEntity<Object> handleUnauthorized(CandidateManagerUnauthorizedException ex) {
    log.error("Unauthorized: {}", ex.getMessage());
    if (ex.getCode() == null){
      CandidateManagerError candidateManagerError = CandidateManagerError.builder().httpStatus(HttpStatus.UNAUTHORIZED)
              .code(CandidateManagerConstants.PREFIX_CLIENT_ERROR + CandidateManagerConstants.UNAUTHORIZED)
              .message(ex.getMessage()).build();
      return buildResponseEntity(candidateManagerError);
    }else{
      CandidateManagerError candidateManagerError = CandidateManagerError.builder().httpStatus(ex.getHttpStatus())
              .code(ex.getCode())
              .message(ex.getMessage()).build();
      return buildResponseEntity(candidateManagerError);
    }
  }

  @ExceptionHandler(CandidateManagerGenericServerException.class)
  protected ResponseEntity<Object> handleGenericServerError(CandidateManagerGenericServerException ex) {
    CandidateManagerError candidateManagerError = null;
    if (ex.getCode() != null) {
      candidateManagerError = CandidateManagerError.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
          .code(ex.getCode()).build();
    } else {
      candidateManagerError = CandidateManagerError.builder().httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
          .code(CandidateManagerConstants.PREFIX_SERVER_ERROR + CandidateManagerConstants.INTERNAL_SERVER_ERROR)
          .build();
    }
    candidateManagerError.setMessage(ex.getMessage());
    log.error("Internal Server Error: {}", ex.getMessage());
    return buildResponseEntity(candidateManagerError);
  }

  @ExceptionHandler(CandidateManagerGenericClientException.class)
  protected ResponseEntity<Object> handleGenericClientException(CandidateManagerGenericClientException ex) {
    log.error("Client Error: {}", ex.getMessage());
    return buildResponseEntity(CandidateManagerError.builder().httpStatus(ex.getHttpStatus())
        .code(CandidateManagerConstants.PREFIX_CLIENT_ERROR).message(ex.getMessage())
        .subErrors(ex.getSubErrors()).build());
  }
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleError(Exception ex) {
    log.error("Server Error: {}", ex.getMessage());
    return buildResponseEntity(CandidateManagerError.builder()
        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        .code(CandidateManagerConstants.PREFIX_SERVER_ERROR + CandidateManagerConstants.INTERNAL_SERVER_ERROR)
        .message(ex.getMessage()).build());
  }

  private ResponseEntity<Object> buildResponseEntity(CandidateManagerError candidateManagerError) {
    return new ResponseEntity<>(candidateManagerError, candidateManagerError.getHttpStatus());
  }

  protected List<CandidateManagerSubError> fillValidationErrorsFrom(
      MethodArgumentNotValidException argumentNotValid) {
    List<CandidateManagerSubError> subErrorCollection = new ArrayList<>();
    argumentNotValid.getBindingResult().getFieldErrors().get(0).getRejectedValue();
    argumentNotValid.getBindingResult().getFieldErrors().stream().forEach((objError) -> {
      subErrorCollection.add(CandidateManagerValidationError.builder().object(objError.getObjectName())
          .field(objError.getField()).rejectValue(objError.getRejectedValue())
          .message(objError.getDefaultMessage()).build());
    });
    return subErrorCollection;
  }

}
