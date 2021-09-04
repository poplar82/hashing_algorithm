package com.topolski.hashing_algorithm.services;

import com.topolski.hashing_algorithm.model.User;
import com.topolski.hashing_algorithm.model.UserRole;
import com.topolski.hashing_algorithm.model.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public void addNewUser(String username,
                           String password,
                           String firstName,
                           String lastName,
                           String tel,
                           String email,
                           boolean admin) {
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .firstName(firstName)
                .lastName(lastName)
                .tel(tel)
                .email(email)
                .creationDate(
                        LocalDateTime.now().format(
                                DateTimeFormatter
                                        .ofPattern("yyyy-MM-dd HH:mm:ss")))
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .role(UserRole.USER.getRole())
                .build();
        if (admin) {
            if (userRepo.existsByRole(UserRole.MASTER.getRole())) {
                user.setRole(UserRole.ADMIN.getRole());
            } else {
                user.setRole(UserRole.MASTER.getRole());
            }
        }
        userRepo.save(user);
    }

    public boolean checkUserIfUsernameExists(String username) {
        return userRepo.existsByUsername(username);
    }
    public boolean checkUserIfEmailExists(String email) {
        return userRepo.existsByEmail(email);
    }

}
