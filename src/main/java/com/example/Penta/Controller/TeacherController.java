package com.example.Penta.Controller;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Teacher;
import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.Service.EMSUserDetailsService;
import com.example.Penta.Service.TeacherService;
import com.example.Penta.dto.TeacherRequest;
import com.example.Penta.dto.TeacherResponse;
import com.example.Penta.dto.TeacherUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class TeacherController {
    @Autowired
    private EMSUserRepository emsUserRepository;
    @Autowired
    private EMSUserDetailsService emsUserService;
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/teacher/save/{user_id}")
    public ResponseEntity<?> createTeacher(@PathVariable("user_id") UUID user_id, @RequestBody TeacherRequest request){
        Optional<EMSUser> optionalEMSUser = emsUserRepository.findByUserId(user_id);
        Teacher teacher = new Teacher();
        if(optionalEMSUser.isPresent()){
            EMSUser user = optionalEMSUser.get();
            teacher.setEmsUser(user);
            teacher.setDesignation(request.getDesignation());
            teacher.setFaculty_name(request.getFaculty_name());
            teacherService.createTeacher(teacher);
            return new ResponseEntity<>("Teacher Created",HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>("Teacher can't create",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/teacher/get/{user_id}")
    public ResponseEntity<?> getTeacherInfo(@PathVariable("user_id") UUID user_id) {
        Optional<Teacher> optionalResponse = teacherService.getTeacherInfo(user_id);
        TeacherResponse teacherResponse = new TeacherResponse();
        if(optionalResponse.isPresent()){
            Teacher teacher = optionalResponse.get();

            teacherResponse.setStatus(teacher.getEmsUser().getStatus());
            teacherResponse.setName(teacher.getEmsUser().getName());
            teacherResponse.setPhone(teacher.getEmsUser().getPhone());
            teacherResponse.setEmail(teacher.getEmsUser().getEmail());
            teacherResponse.setDesignation(teacher.getDesignation());
            teacherResponse.setFaculty_name(teacher.getFaculty_name());
            teacherResponse.setRole_id(teacher.getEmsUser().getRole().getRole_id());
            return new ResponseEntity<>(teacherResponse,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Teacher Not Found",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/teacher/update/{user_id}")
    public ResponseEntity<?> updateTeacherInfo(@PathVariable("user_id") UUID user_id,@RequestBody TeacherUpdateRequest request){
        String response = teacherService.updateTeacherInfo(user_id,request);
        if(response.equals("Teacher User Updated")){
            return new ResponseEntity<>(response,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
