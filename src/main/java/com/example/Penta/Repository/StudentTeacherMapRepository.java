package com.example.Penta.Repository;

import com.example.Penta.Entity.StudentTeacherRequestMap;
import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentTeacherMapRepository extends JpaRepository<StudentTeacherRequestMap,Integer> {

    @Modifying
    @Query("DELETE FROM StudentTeacherRequestMap m WHERE m.student_user_id = :student_user_id" +
            " and m.teacher_user_id = :teacher_user_id")
    void deleteStudentTeacherMapByStudentUserId(@Param("student_user_id") UUID student_user_id,
                                                @Param("teacher_user_id") UUID teacher_user_id);

    @Modifying
    @Query("DELETE FROM StudentTeacherRequestMap m WHERE m.student_user_id = :student_user_id")
    void deleteStudentById(@Param("student_user_id") UUID student_user_id);
}
