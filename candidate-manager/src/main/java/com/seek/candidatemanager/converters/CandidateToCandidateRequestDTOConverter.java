package com.seek.candidatemanager.converters;

import com.seek.candidatemanager.constants.CandidateManagerConstants;
import com.seek.candidatemanager.domains.Candidate;
import com.seek.candidatemanager.tdos.request.CandidateRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CandidateToCandidateRequestDTOConverter extends AbstractConverter<Candidate, CandidateRequestDTO>{


    @Override
    public CandidateRequestDTO fromEntity(Candidate entity) {
        return null;
    }

    @Override
    public Candidate fromDTO(CandidateRequestDTO dto) {
        return Candidate.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .expectedSalary(dto.getExpectedSalary())
                .state(CandidateManagerConstants.ACTIVE_STATE)
                .registrationDate(LocalDateTime.now())
                .build();
    }
}
