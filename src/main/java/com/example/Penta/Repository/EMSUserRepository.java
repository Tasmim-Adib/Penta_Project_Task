package com.example.Penta.Repository;

import com.example.Penta.Entity.EMSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EMSUserRepository extends JpaRepository<EMSUser, UUID> {
    Optional<EMSUser> findByEmail(String email);
}
