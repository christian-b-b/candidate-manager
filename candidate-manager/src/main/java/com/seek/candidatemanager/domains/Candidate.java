package com.seek.candidatemanager.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_CANDIDATE")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCandidate")
    private Long idCandidate;
    @Column(name = "name")
    private String name ;
    @Column(name = "email")
    private String email;
    @Column(name = "gender")
    private String gender;
    @Column(name = "expectedSalary")
    private BigDecimal expectedSalary;

    @Column(name = "state")
    private Integer state;

    @Column(name = "registrationDate")
    private LocalDateTime registrationDate;
}
