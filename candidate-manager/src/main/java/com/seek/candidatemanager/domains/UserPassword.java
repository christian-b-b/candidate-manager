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
@Table(name = "T_USER_PASSWORD")
public class UserPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUserPassword")
    private Long idUserPassword;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;

    @Column(name = "password")
    private String password;

    @Column(name = "state")
    private Integer state;

    @Column(name = "registrationDate")
    private LocalDateTime registrationDate;
}
