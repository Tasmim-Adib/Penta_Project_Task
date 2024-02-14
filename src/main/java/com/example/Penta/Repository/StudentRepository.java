package com.example.Penta.Repository;

import com.example.Penta.Entity.Student;
import com.example.Penta.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Student s JOIN FETCH s.emsUser WHERE s.emsUser.user_id = :user_id")
    Optional<Student> findStudentByUserId(@Param("user_id") UUID user_id);

    @Query("SELECT s FROM Student s WHERE s.advisor.user_id = :advisor_id")
    List<Student> findAllByAdvisor_EmsUser_Id(@Param("advisor_id") UUID advisor_id);

    @Query("SELECT s FROM Student s JOIN StudentTeacherRequestMap m ON m.student_user_id = " +
            "s.emsUser.user_id WHERE m.teacher_user_id = :teacher_user_id")
    List<Student> findAllStudentWhoRequestTeacher(@Param("teacher_user_id") UUID teacher_user_id);
}
