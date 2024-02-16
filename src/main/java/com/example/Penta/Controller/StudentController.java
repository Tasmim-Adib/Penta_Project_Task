package com.example.Penta.Controller;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Student;
import com.example.Penta.Entity.Teacher;
import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.Repository.StudentRepository;
import com.example.Penta.Repository.TeacherRepository;
import com.example.Penta.Service.EMSUserDetailsService;
import com.example.Penta.Service.StudentService;
import com.example.Penta.Service.StudentTeacherMapService;
import com.example.Penta.Service.TeacherService;
import com.example.Penta.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class StudentController {

    @Autowired
    private EMSUserRepository emsUserRepository;
    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentTeacherMapService studentTeacherMapService;

    //save a student info
    @PostMapping("/student/save/{user_id}")
    public ResponseEntity<?> createStudent(@PathVariable("user_id") UUID user_id, @RequestBody StudentRequest request){
        Optional<EMSUser> optionalEMSUser = emsUserRepository.findByUserId(user_id);
        Student student = new Student();
        if(optionalEMSUser.isPresent()){
            EMSUser user = optionalEMSUser.get();

            student.setStudent_id(request.getStudent_id());
            student.setDepartment_name(request.getDepartment_name());
            student.setBatch_no(request.getBatch_no());
            student.setEmsUser(user);
            studentService.createStudent(student);
            return new ResponseEntity<>("Student is Created",HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Student is not Created",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //get a student info
    @GetMapping("/student/get/{user_id}")
    public ResponseEntity<?> getStudentInfo(@PathVariable("user_id") UUID user_id){
        Optional<Student> optionalStudent = studentService.getStudentInfo(user_id);
        StudentResponse studentResponse = new StudentResponse();
        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            studentResponse.setUser_id(user_id);
            studentResponse.setStudent_id(student.getStudent_id());
            studentResponse.setBatch_no(student.getBatch_no());
            studentResponse.setDepartment_name(student.getDepartment_name());
            studentResponse.setName(student.getEmsUser().getName());
            studentResponse.setStatus(student.getEmsUser().getStatus());
            studentResponse.setEmail(student.getEmsUser().getEmail());
            studentResponse.setPhone(student.getEmsUser().getPhone());
            studentResponse.setRole_id(student.getEmsUser().getRole().getRole_id());

            if(student.getAdvisor() != null){
                Optional<Teacher> optionalTeacher = teacherService.getTeacherInfo(student.getAdvisor().getUser_id());
                if(optionalTeacher.isPresent()){
                    Teacher teacher = optionalTeacher.get();
                    studentResponse.setAdvisor(teacher.getEmsUser().getName());
                }
            }
            return new ResponseEntity<>(studentResponse,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Student Not Found",HttpStatus.NOT_FOUND);
        }
    }

    //update student advisor info
    @PutMapping("/student/update/advisor/{user_id}")
    public ResponseEntity<?> updateAdvisorInfo(@PathVariable("user_id") UUID user_id, @RequestBody AdvisorUpdateRequest request){
        String response = studentService.updateAdvisorInfo(request.getAdvisor(), user_id);
        if(response.equals("Advisor Info Updated")){
            String ano = studentTeacherMapService.deleteStudentRequestById(user_id);
            if(ano.equals("Request Deleted For A Student")){
                return new ResponseEntity<>(response,HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>("Advisor Added but Request Not Deleted",HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // remove a student from Advice list
    @PutMapping("/student/remove/advisor/{student_user_id}")
    public ResponseEntity<?> removeStudentFromAdviceList(@PathVariable("student_user_id")UUID studen_user_id){
        String response = studentService.removeStudentFromAdviceList(studen_user_id);
        if(response.equals("Student is removed from Advice List")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //Find all student with particular advisor
    @GetMapping("/student/get/advisor/{teacher_user_id}")
    public List<AllStudentWithAdvisorResponse> getAllStudentWithAdvisor(@PathVariable("teacher_user_id") UUID teacher_user_id){
        return studentService.findAllStudentWithAdvisor(teacher_user_id);
    }

    //update a student info
    @PutMapping("/student/update/{user_id}")
    public ResponseEntity<?> updateStudentInfo(@PathVariable("user_id") UUID user_id, @RequestBody StudentUpdateRequest request){
        String response = studentService.updateStudentInfo(user_id,request);
        if(response.equals("Student User Updated")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // find student who request a teacher to be advisor
    @GetMapping("/student/get/request/advisor/{teacher_user_id}")
    public List<AllStudentWithAdvisorResponse> getAllStudentWhoRequest(@PathVariable("teacher_user_id")UUID teacher_user_id){
        return studentService.findAllStudentWhoRequest(teacher_user_id);
    }


}
