package com.example.Penta.Repository;

import com.example.Penta.Entity.EMSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EMSUserRepository extends JpaRepository<EMSUser, UUID> {

    Optional<EMSUser> findByEmail(String email);

    @Query("SELECT e FROM EMSUser e WHERE e.user_id = :id")
    Optional<EMSUser> findByUserId(UUID id);
}
