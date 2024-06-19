package com.myblog_rest_api.Repositories;

import com.myblog_rest_api.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>findByEmail(String email);
    Optional<User>findByEmailOrUsername(String email,String username);
    Optional<User>findByUsername(String username);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
