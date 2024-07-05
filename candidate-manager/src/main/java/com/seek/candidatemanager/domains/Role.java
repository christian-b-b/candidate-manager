package com.seek.candidatemanager.domains;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_ROLE")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRole")
    private Long idRole;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private Integer state;

    @Column(name = "registrationDate")
    private LocalDateTime registrationDate;
}
