package com.seek.candidatemanager.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CandidateManagerUnauthorizedException extends RuntimeException {
    private String code;
    private HttpStatus httpStatus;
    public CandidateManagerUnauthorizedException(String code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
