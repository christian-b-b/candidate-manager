package com.seek.candidatemanager.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CandidateManagerNotFoundException extends RuntimeException {
    private String code;
}
