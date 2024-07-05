package com.seek.candidatemanager.services.impl;

import com.seek.candidatemanager.converters.CandidateToCandidateRequestDTOConverter;
import com.seek.candidatemanager.converters.CandidateToCandidateResponseDTOConverter;
import com.seek.candidatemanager.domains.Candidate;
import com.seek.candidatemanager.errorhandler.CandidateManagerGenericClientException;
import com.seek.candidatemanager.repositories.CandidateRepository;
import com.seek.candidatemanager.services.CandidateService;
import com.seek.candidatemanager.tdos.request.CandidateRequestDTO;
import com.seek.candidatemanager.tdos.response.CandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    private CandidateToCandidateResponseDTOConverter candidateToCandidateResponseDTOConverter;
    @Autowired
    private CandidateToCandidateRequestDTOConverter candidateToCandidateRequestDTOConverter;

    @Override
    public List<CandidateResponseDTO> findAll() {
        return candidateToCandidateResponseDTOConverter.fromEntity(candidateRepository.findAll());
    }

    @Override
    public CandidateResponseDTO findById(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(
                        ()-> new CandidateManagerGenericClientException("Candidate not found", HttpStatus.NOT_FOUND,null,null));

        return candidateToCandidateResponseDTOConverter.fromEntity(candidate);
    }

    @Override
    public CandidateResponseDTO save(CandidateRequestDTO candidateRequestDTO) {
        Candidate candidate = candidateToCandidateRequestDTOConverter.fromDTO(candidateRequestDTO);
        return candidateToCandidateResponseDTOConverter.fromEntity(candidateRepository.save(candidate));
    }

    @Override
    public CandidateResponseDTO update(Long id, CandidateRequestDTO candidateRequestDTO) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(
                        ()-> new CandidateManagerGenericClientException("Candidate not found", HttpStatus.NOT_FOUND,null,null));
        candidate.setName(candidateRequestDTO.getName());
        candidate.setEmail(candidateRequestDTO.getEmail());
        candidate.setGender(candidateRequestDTO.getGender());
        candidate.setExpectedSalary(candidateRequestDTO.getExpectedSalary());
        candidateRepository.save(candidate);
        return candidateToCandidateResponseDTOConverter.fromEntity(candidate);
    }

    @Override
    public void deleteById(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(
                ()-> new CandidateManagerGenericClientException("Candidate not found", HttpStatus.NOT_FOUND,null,null));
        candidateRepository.delete(candidate);
    }
}
