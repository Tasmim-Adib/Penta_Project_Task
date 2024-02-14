package com.example.Penta.Repository;

import com.example.Penta.Entity.MailKeyMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailKeyMapRepository extends JpaRepository<MailKeyMap,Integer> {
    @Query("SELECT e FROM MailKeyMap e WHERE e.email = :email")
    Optional<MailKeyMap> findMailKey(String email);

    @Modifying
    @Query("DELETE FROM MailKeyMap e WHERE e.email = :email")
    void deleteByEmail(String email);
}
