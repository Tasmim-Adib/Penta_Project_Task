package com.example.Penta.Repository;

import com.example.Penta.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    @Query("SELECT t FROM Teacher t JOIN FETCH t.emsUser WHERE t.emsUser.user_id = :user_id")
    Optional<Teacher> findTeacherByUserId(@Param("user_id") UUID user_id);
}
