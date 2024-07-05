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
@Table(name = "T_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Long idUser;
    @Column(name = "name")
    private String name;

    @Column(name = "state")
    private Integer state;

    @Column(name = "registrationDate")
    private LocalDateTime registrationDate;
}
