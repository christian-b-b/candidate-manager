package com.seek.candidatemanager.repositories;


import com.seek.candidatemanager.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByNameAndState(String name, Integer state);
}
