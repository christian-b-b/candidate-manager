package com.seek.candidatemanager.controllers;

import com.seek.candidatemanager.constants.CandidateManagerConstants;
import com.seek.candidatemanager.services.CandidateService;
import com.seek.candidatemanager.tdos.request.CandidateRequestDTO;
import com.seek.candidatemanager.tdos.response.CandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CandidateManagerConstants.API_VERSION + CandidateManagerConstants.RESOURCE_CANDIDATES)
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public ResponseEntity<List<CandidateResponseDTO>> getAllCandidates(){
        return new ResponseEntity<List<CandidateResponseDTO>>(candidateService.findAll(),null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> getCandidateById(@PathVariable("id") Long id){
        return new ResponseEntity<CandidateResponseDTO>(candidateService.findById(id),null,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CandidateResponseDTO> createCandidate(@RequestBody CandidateRequestDTO candidateRequestDTO){
        return new ResponseEntity<CandidateResponseDTO>(candidateService.save(candidateRequestDTO),null,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> updateCandidate(@PathVariable("id") Long id, @RequestBody CandidateRequestDTO candidateRequestDTO){
        return new ResponseEntity<CandidateResponseDTO>(candidateService.update(id, candidateRequestDTO), null, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCandidateById(@PathVariable("id") Long id){
        candidateService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
