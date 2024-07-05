package com.seek.candidatemanager.services;


import com.seek.candidatemanager.tdos.request.CandidateRequestDTO;
import com.seek.candidatemanager.tdos.response.CandidateResponseDTO;

import java.util.List;

public interface CandidateService {
    List<CandidateResponseDTO> findAll();
    CandidateResponseDTO findById(Long id);
    CandidateResponseDTO save(CandidateRequestDTO candidateRequestDTO);
    CandidateResponseDTO update(Long id,CandidateRequestDTO candidateRequestDTO);
    void deleteById(Long id);
}
