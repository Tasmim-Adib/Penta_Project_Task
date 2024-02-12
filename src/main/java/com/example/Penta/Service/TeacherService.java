package com.example.Penta.Service;

import com.example.Penta.Entity.Teacher;
import com.example.Penta.Repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    public String createTeacher(Teacher teacher){
        teacherRepository.save(teacher);
        return "New Teacher Added";
    }

    public Optional<Teacher> getTeacherInfo(UUID user_id){
        return teacherRepository.findTeacherByUserId(user_id);
    }
}
