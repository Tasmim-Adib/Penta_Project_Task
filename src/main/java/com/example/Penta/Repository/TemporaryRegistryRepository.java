package com.example.Penta.Repository;

import com.example.Penta.Entity.TemporaryRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemporaryRegistryRepository extends JpaRepository<TemporaryRegistration,Integer> {

    @Query("SELECT t FROM TemporaryRegistration t WHERE t.email = :email")
    Optional<TemporaryRegistration> findByUserMail(@Param("email") String email);

    @Modifying
    @Query("DELETE FROM TemporaryRegistration t WHERE t.email = :email")
    void deleteFromTemporaryTable(@Param("email") String email);
}
