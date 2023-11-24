package com.example.tutorial.repository;

import com.example.tutorial.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {
    @Query("SELECT u FROM User u where u.email = ?1 or u.phone = ?1")
    public Optional<User> findUserByAccount(String account);

    public Boolean existsByPhone(String phone);
    public Boolean existsByEmail(String email);
}
