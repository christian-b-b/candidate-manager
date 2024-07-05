package com.seek.candidatemanager.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CandidateManagerValidationError extends CandidateManagerSubError{
  private String object;
  private String field;
  private Object rejectValue;
  private String message;

}
