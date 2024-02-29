package com.epam.esm.repository;

import com.epam.esm.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByName(String name);

    Boolean existsByName(String name);

    Boolean existsByEmail(String email);
}