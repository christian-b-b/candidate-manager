package com.seek.candidatemanager.repositories;

import com.seek.candidatemanager.domains.User;
import com.seek.candidatemanager.domains.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findUserRoleByUser(User user);
}
