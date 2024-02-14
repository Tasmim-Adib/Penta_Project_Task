package com.example.Penta.Service;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Student;
import com.example.Penta.Entity.Teacher;
import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.Repository.TeacherRepository;
import com.example.Penta.dto.AllTeacherResponse;
import com.example.Penta.dto.StudentUpdateRequest;
import com.example.Penta.dto.TeacherUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private EMSUserRepository emsUserRepository;
    public String createTeacher(Teacher teacher){
        teacherRepository.save(teacher);
        return "New Teacher Added";
    }

    public Optional<Teacher> getTeacherInfo(UUID user_id){
        return teacherRepository.findTeacherByUserId(user_id);
    }

    @Transactional
    public String updateTeacherInfo(UUID teacherUser_id, TeacherUpdateRequest request){
        Optional<Teacher> optionalTeacher = teacherRepository.findTeacherByUserId(teacherUser_id);

        if(optionalTeacher.isPresent()){
            Teacher teacher = optionalTeacher.get();

            if(request.getFaculty_name() != null){
                teacher.setFaculty_name(request.getFaculty_name());
            }
            if(request.getDesignation() != null){
                teacher.setDesignation(request.getDesignation());
            }
            teacherRepository.save(teacher);

            EMSUser emsUser = teacher.getEmsUser();
            if(request.getName() != null){
                emsUser.setName(request.getName());
            }
            if(request.getPhone() != null){
                emsUser.setPhone(request.getPhone());
            }

            emsUserRepository.save(emsUser);

            return "Teacher User Updated";

        }
        else{
            return "Teacher is not Updated";
        }

    }

    public List<AllTeacherResponse> findAllTeacher(){
        List<Teacher> teachers = teacherRepository.findAll();
        return teachers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AllTeacherResponse> findAllTeacherWhomStudentRequest(UUID student_user_id){
        List<Teacher> teachers = teacherRepository.findAllTeacherWhomAStudentRequest(student_user_id);
        return teachers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AllTeacherResponse> findAllTeacherWhomStudentNotRequest(UUID student_user_id){
        List<Teacher> teachers = teacherRepository.findAllTeacherWhomAStudentNOTRequest(student_user_id);
        return teachers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private AllTeacherResponse convertToDTO(Teacher teacher){
        AllTeacherResponse dto = new AllTeacherResponse();
        dto.setFaculty_name(teacher.getFaculty_name());
        dto.setDesignatoin(teacher.getDesignation());
        EMSUser user = teacher.getEmsUser();
        dto.setPhone(user.getPhone());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setUser_id(user.getUser_id());
        return dto;
    }
}
