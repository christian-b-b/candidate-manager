package com.seek.candidatemanager.repositories;

import com.seek.candidatemanager.domains.User;
import com.seek.candidatemanager.domains.UserPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPasswordRepository extends JpaRepository<UserPassword,Long> {
    Optional<UserPassword> findUserPasswordByUserAndState(User user, Integer state);
}
