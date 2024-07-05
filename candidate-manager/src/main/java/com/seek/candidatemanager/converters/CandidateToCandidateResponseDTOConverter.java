package com.seek.candidatemanager.converters;

import com.seek.candidatemanager.domains.Candidate;
import com.seek.candidatemanager.tdos.response.CandidateResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CandidateToCandidateResponseDTOConverter extends AbstractConverter<Candidate, CandidateResponseDTO>{
    @Override
    public CandidateResponseDTO fromEntity(Candidate entity) {
        return CandidateResponseDTO.builder()
                .idCandidate(entity.getIdCandidate())
                .name(entity.getName())
                .email(entity.getEmail())
                .gender(entity.getGender())
                .expectedSalary(entity.getExpectedSalary())
                .build();
    }

    @Override
    public Candidate fromDTO(CandidateResponseDTO dto) {
        return null;
    }
}
