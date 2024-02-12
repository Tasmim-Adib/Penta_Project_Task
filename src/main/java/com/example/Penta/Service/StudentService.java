package com.example.Penta.Service;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Student;
import com.example.Penta.Entity.Teacher;
import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.Repository.StudentRepository;
import com.example.Penta.Repository.TeacherRepository;
import com.example.Penta.dto.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EMSUserRepository emsUserRepository;
    @Autowired
    private TeacherService teacherService;

    public String createStudent(Student student){
        studentRepository.save(student);
        return "New Student Added";
    }

    public Optional<Student> getStudentInfo(UUID user_id){
        return studentRepository.findStudentByUserId(user_id);
    }

    public String updateAdvisorInfo(UUID teacherUserId, UUID user_id){
        Optional<Student> optionalStudent = studentRepository.findStudentByUserId(user_id);
        Optional<Teacher> optionalTeacher = teacherService.getTeacherInfo(teacherUserId);
        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            if(optionalTeacher.isPresent()){
                Teacher teacher = optionalTeacher.get();
                student.setAdvisor(teacher.getEmsUser());
                studentRepository.save(student);
                return "Advisor Info Updated";
            }
            else{
                throw new UsernameNotFoundException("Advisor not Found");
            }

        }
        throw new UsernameNotFoundException("Student not Found");
    }
}
