package com.example.sweater.Greetings.repository;

import com.example.sweater.Greetings.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
