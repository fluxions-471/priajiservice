package com.priaji.security.repository;

import com.priaji.security.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {


    Optional<UserCredential> findByName(String username);
}
