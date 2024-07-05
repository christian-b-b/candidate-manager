package com.seek.candidatemanager.errorhandler;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class CandidateManagerGenericServerException extends  RuntimeException {

  private String code;
  private HttpStatus httpStatus;

  public CandidateManagerGenericServerException(String message, HttpStatus httpStatus){
    super(message);
    this.httpStatus = httpStatus;
  }

  public CandidateManagerGenericServerException(String message) {
    super(message);
  }
}
