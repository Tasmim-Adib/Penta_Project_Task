package com.example.Penta.Repository;

import com.example.Penta.Entity.Student;
import com.example.Penta.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Student s JOIN FETCH s.emsUser WHERE s.emsUser.user_id = :user_id")
    Optional<Student> findStudentByUserId(@Param("user_id") UUID user_id);




}
