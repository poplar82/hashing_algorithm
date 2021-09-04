package com.topolski.hashing_algorithm.model.repository;

import com.topolski.hashing_algorithm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByRole(String role);
    User findAllByUsername(String username);
    User findByRole(String role);
}
