package com.lcdw.electronic.store.repositories;

import com.lcdw.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);
    
    List<User> findByNameContaining(String keyword);
}
