package com.example.boardpractice.auth.repository;

import com.example.boardpractice.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
