package com.example.Penta.Service;

import com.example.Penta.Entity.Student;
import com.example.Penta.Entity.StudentTeacherRequestMap;
import com.example.Penta.Repository.StudentTeacherMapRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StudentTeacherMapService {
    @Autowired
    private StudentTeacherMapRepository studentTeacherMapRepository;

    @Autowired
    private StudentService studentService;

    //Save a request
    public String saveData(StudentTeacherRequestMap data){
        Optional<Student> optionalStudent = studentService.getStudentInfo(data.getStudent_user_id());
        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            if(student.getAdvisor() == null){
                studentTeacherMapRepository.save(data);
                return "Request Sent";
            }else{
                return "Already have an Advisor";
            }

        }else{
            return "Student not exist";
        }

    }

    @Transactional
    public String deleteStudentTeacherMapId(UUID student_user_id,UUID teacher_user_id){
        studentTeacherMapRepository.deleteStudentTeacherMapByStudentUserId(student_user_id,teacher_user_id);
        return "Request Deleted";
    }

    @Transactional
    public String deleteStudentRequestById(UUID student_user_id){
        studentTeacherMapRepository.deleteStudentById(student_user_id);
        return "Request Deleted For A Student";
    }
}
