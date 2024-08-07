package com.seek.candidatemanager.tdos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponseDTO {
    private Long idCandidate;
    private String name ;
    private String email;
    private String gender;
    private BigDecimal expectedSalary;

}
