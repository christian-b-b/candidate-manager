package com.seek.candidatemanager.repositories;

import com.seek.candidatemanager.domains.Candidate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class CandidateRepositoryTest {
    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    @DisplayName("Return all candidates")
    void testFindAll() {
        Candidate candidate1 = new Candidate();
        candidate1.setName("Christian Baldeón Baldeón");
        candidate1.setEmail("baldeon.bc@gmail.com");
        candidate1.setGender("male");
        candidate1.setExpectedSalary(BigDecimal.valueOf(50000));
        candidate1.setState(1);
        candidate1.setRegistrationDate(LocalDateTime.now());

        Candidate candidate2 = new Candidate();
        candidate2.setName("Diego Torrez Gimenez");
        candidate2.setEmail("diego.tg@gmail.com");
        candidate2.setGender("male");
        candidate2.setExpectedSalary(BigDecimal.valueOf(60000));
        candidate2.setState(1);
        candidate2.setRegistrationDate(LocalDateTime.now());

        candidateRepository.save(candidate1);
        candidateRepository.save(candidate2);

        List<Candidate> candidates = candidateRepository.findAll();

        assertEquals(2, candidates.size());
        assertEquals("Christian Baldeón Baldeón", candidates.get(0).getName());
        assertEquals("Diego Torrez Gimenez", candidates.get(1).getName());
    }

    @Test
    @DisplayName("Return a candidate by id")
    void testFindById() {
        Candidate candidate = new Candidate();
        candidate.setName("Christian Baldeón Baldeón");
        candidate.setEmail("baldeon.bc@gmail.com");
        candidate.setGender("male");
        candidate.setExpectedSalary(BigDecimal.valueOf(50000));
        candidate.setState(1);
        candidate.setRegistrationDate(LocalDateTime.now());

        Candidate savedCandidate = candidateRepository.save(candidate);

        Optional<Candidate> foundCandidate = candidateRepository.findById(savedCandidate.getIdCandidate());

        assertTrue(foundCandidate.isPresent());
        assertEquals("Christian Baldeón Baldeón", foundCandidate.get().getName());
    }

    @Test
    @DisplayName("Create a candidate")
    void testSave() {

        Candidate candidate = new Candidate();
        candidate.setName("Christian Baldeón Baldeón");
        candidate.setEmail("baldeon.bc@gmail.com");
        candidate.setGender("male");
        candidate.setExpectedSalary(BigDecimal.valueOf(50000));
        candidate.setState(1);
        candidate.setRegistrationDate(LocalDateTime.now());

        Candidate savedCandidate = candidateRepository.save(candidate);

        assertNotNull(savedCandidate);
        assertNotNull(savedCandidate.getIdCandidate());
        assertEquals("Christian Baldeón Baldeón", savedCandidate.getName());
    }

    @Test
    @DisplayName("Delete a candidate")
    void testDelete() {
        Candidate candidate = new Candidate();
        candidate.setName("Christian Baldeón Baldeón");
        candidate.setEmail("baldeon.bc@gmail.com");
        candidate.setGender("male");
        candidate.setExpectedSalary(BigDecimal.valueOf(50000));
        candidate.setState(1);
        candidate.setRegistrationDate(LocalDateTime.now());

        Candidate savedCandidate = candidateRepository.save(candidate);
        Long candidateId = savedCandidate.getIdCandidate();

        candidateRepository.deleteById(candidateId);

        Optional<Candidate> deletedCandidate = candidateRepository.findById(candidateId);

        assertFalse(deletedCandidate.isPresent());
    }
}
