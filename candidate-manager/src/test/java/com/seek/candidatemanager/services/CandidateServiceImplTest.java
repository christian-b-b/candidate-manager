package com.seek.candidatemanager.services;

import com.seek.candidatemanager.converters.CandidateToCandidateRequestDTOConverter;
import com.seek.candidatemanager.converters.CandidateToCandidateResponseDTOConverter;
import com.seek.candidatemanager.domains.Candidate;
import com.seek.candidatemanager.repositories.CandidateRepository;
import com.seek.candidatemanager.services.impl.CandidateServiceImpl;
import com.seek.candidatemanager.tdos.request.CandidateRequestDTO;
import com.seek.candidatemanager.tdos.response.CandidateResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
@ActiveProfiles("test")
public class CandidateServiceImplTest {
    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private CandidateToCandidateRequestDTOConverter candidateToCandidateRequestDTOConverter;

    @Mock
    private CandidateToCandidateResponseDTOConverter candidateToCandidateResponseDTOConverter;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create a candidate")
    void testAddCandidate() {
        //Arrange
        Candidate candidate = new Candidate();
        candidate.setName("Christian Baldeón Baldeón");
        candidate.setEmail("baldeon.bc@gmail.com");
        candidate.setGender("male");
        candidate.setExpectedSalary(BigDecimal.valueOf(50000));
        candidate.setState(1);
        candidate.setRegistrationDate(LocalDateTime.now());

        Candidate candidate1 = new Candidate();
        candidate1.setIdCandidate(1L);
        candidate1.setName("Christian Baldeón Baldeón");
        candidate1.setEmail("baldeon.bc@gmail.com");
        candidate1.setGender("male");
        candidate1.setExpectedSalary(BigDecimal.valueOf(50000));
        candidate1.setState(1);
        candidate1.setRegistrationDate(LocalDateTime.now());

        CandidateRequestDTO requestDTO = new CandidateRequestDTO();
        requestDTO.setName("Christian Baldeón Baldeón");
        requestDTO.setEmail("baldeon.bc@gmail.com");
        requestDTO.setGender("male");
        requestDTO.setExpectedSalary(BigDecimal.valueOf(50000));

        CandidateResponseDTO responseDTO = new CandidateResponseDTO();
        responseDTO.setIdCandidate(1L);
        responseDTO.setName("Christian Baldeón Baldeón");
        responseDTO.setEmail("baldeon.bc@gmail.com");
        responseDTO.setGender("male");
        responseDTO.setExpectedSalary(BigDecimal.valueOf(50000));

        when(candidateToCandidateRequestDTOConverter.fromDTO(any(CandidateRequestDTO.class))).thenReturn(candidate);
        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate1);
        when(candidateToCandidateResponseDTOConverter.fromEntity(any(Candidate.class))).thenReturn(responseDTO);

        //Act
        CandidateResponseDTO savedCandidate = candidateService.save(requestDTO);

        //Asserts
        assertNotNull(savedCandidate);
        assertEquals("Christian Baldeón Baldeón", savedCandidate.getName());
    }

    @Test
    @DisplayName("Update a candidate")
    void testUpdateCandidate() {
        //Arrange
        Long candidateId = 1L;
        Candidate candidate = new Candidate();
        candidate.setIdCandidate(candidateId);
        candidate.setName("Christian Baldeón Baldeón");
        candidate.setEmail("baldeon.bc@gmail.com");
        candidate.setGender("male");
        candidate.setExpectedSalary(BigDecimal.valueOf(60000));
        candidate.setState(1);
        candidate.setRegistrationDate(LocalDateTime.now());

        CandidateRequestDTO requestDTO = new CandidateRequestDTO();
        requestDTO.setName("Leonardo Perez Tello");
        requestDTO.setEmail("leonardo.pt@gmail.com");
        requestDTO.setGender("male");
        requestDTO.setExpectedSalary(BigDecimal.valueOf(60000));

        Candidate candidate1 = new Candidate();
        candidate1.setIdCandidate(candidateId);
        candidate1.setName("Leonardo Perez Tello");
        candidate1.setEmail("leonardo.pt@gmail.com");
        candidate1.setGender("male");
        candidate1.setExpectedSalary(BigDecimal.valueOf(60000));
        candidate1.setState(1);
        candidate1.setRegistrationDate(LocalDateTime.now());

        CandidateResponseDTO responseDTO = new CandidateResponseDTO();
        responseDTO.setIdCandidate(1L);
        responseDTO.setName("Leonardo Perez Tello");
        responseDTO.setEmail("leonardo.pt@gmail.com");
        responseDTO.setGender("male");
        responseDTO.setExpectedSalary(BigDecimal.valueOf(60000));

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate1);
        when(candidateToCandidateResponseDTOConverter.fromEntity(any(Candidate.class))).thenReturn(responseDTO);

        //Act
        CandidateResponseDTO updatedCandidate = candidateService.update(candidateId, requestDTO);

        //Asserts
        assertNotNull(updatedCandidate);
        assertEquals("Leonardo Perez Tello", updatedCandidate.getName());
    }

    @Test
    @DisplayName("Delete a candidate by id")
    void testDeleteCandidate() {
        //Arrange
        Long candidateId = 1L;
        Candidate candidate = new Candidate();
        candidate.setIdCandidate(candidateId);
        candidate.setName("Christian Baldeón Baldeón");

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        doNothing().when(candidateRepository).delete(candidate);
        //Act
        candidateService.deleteById(candidateId);

        //Asserts
        verify(candidateRepository, times(1)).findById(candidateId);
        verify(candidateRepository, times(1)).delete(candidate);
    }

    @Test
    @DisplayName("Return all candidates")
    void testGetAllCandidates() {
        //Arrange
        Candidate candidate1 = new Candidate();
        candidate1.setName("Leonardo Perez Tello");
        candidate1.setEmail("leonardo.pt@gmail.com");

        Candidate candidate2 = new Candidate();
        candidate2.setName("Christian Baldeón Baldeón");
        candidate2.setEmail("baldeon.bc@gmail.com");

        List<Candidate> candidateList = Arrays.asList(candidate1, candidate2);

        CandidateResponseDTO responseDTO1 = new CandidateResponseDTO();
        responseDTO1.setName("Leonardo Perez Tello");
        responseDTO1.setEmail("leonardo.pt@gmail.com");

        CandidateResponseDTO responseDTO2 = new CandidateResponseDTO();
        responseDTO2.setName("Christian Baldeón Baldeón");
        responseDTO2.setEmail("baldeon.bc@gmail.com");

        when(candidateRepository.findAll()).thenReturn(candidateList);
        when(candidateToCandidateResponseDTOConverter.fromEntity(candidateList)).thenReturn(Arrays.asList(responseDTO1, responseDTO2));

        //Act
        List<CandidateResponseDTO> candidates = candidateService.findAll();

        //Asserts
        assertEquals(2, candidates.size());
        assertEquals("Leonardo Perez Tello", candidates.get(0).getName());
        assertEquals("Christian Baldeón Baldeón", candidates.get(1).getName());
    }

    @Test
    @DisplayName("Return a candidate by id")
    void testGetCandidateById() {
        //Arrange
        Long candidateId = 1L;
        Candidate candidate = new Candidate();
        candidate.setIdCandidate(candidateId);
        candidate.setName("Christian Baldeón Baldeón");
        candidate.setEmail("baldeon.bc@gmail.com");

        CandidateResponseDTO responseDTO = new CandidateResponseDTO();
        responseDTO.setName("Christian Baldeón Baldeón");
        responseDTO.setEmail("baldeon.bc@gmail.com");

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(candidateToCandidateResponseDTOConverter.fromEntity(candidate)).thenReturn(responseDTO);

        //Act
        CandidateResponseDTO foundCandidate = candidateService.findById(candidateId);

        //Asserts
        assertNotNull(foundCandidate);
        assertEquals("Christian Baldeón Baldeón", foundCandidate.getName());
    }

}
