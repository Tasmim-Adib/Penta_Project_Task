package com.example.Penta.Repository;

import com.example.Penta.Entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    @Query("SELECT t FROM Teacher t JOIN FETCH t.emsUser WHERE t.emsUser.user_id = :user_id")
    Optional<Teacher> findTeacherByUserId(@Param("user_id") UUID user_id);

    @Query("SELECT t FROM Teacher t JOIN StudentTeacherRequestMap m on m.teacher_user_id" +
            " = t.emsUser.user_id WHERE m.student_user_id = :student_user_id")
    List<Teacher> findAllTeacherWhomAStudentRequest(@Param("student_user_id")UUID student_user_id);

    @Query("SELECT t FROM Teacher t WHERE t.emsUser.user_id IS NULL OR" +
            " t.emsUser.user_id NOT IN " +
            "(SELECT m.teacher_user_id FROM StudentTeacherRequestMap m WHERE m.student_user_id = :student_user_id)")
    List<Teacher> findAllTeacherWhomAStudentNOTRequest(@Param("student_user_id")UUID student_user_id);
}
