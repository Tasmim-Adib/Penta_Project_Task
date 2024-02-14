package com.example.Penta.Service;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Student;
import com.example.Penta.Entity.Teacher;
import com.example.Penta.Repository.EMSUserRepository;
import com.example.Penta.Repository.StudentRepository;
import com.example.Penta.Repository.TeacherRepository;
import com.example.Penta.dto.AllStudentWithAdvisorResponse;
import com.example.Penta.dto.AllTeacherResponse;
import com.example.Penta.dto.StudentRequest;
import com.example.Penta.dto.StudentUpdateRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Transactional
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

    @Transactional
    public String updateStudentInfo(UUID studentUser_id, StudentUpdateRequest request){
        Optional<Student> optionalStudent = studentRepository.findStudentByUserId(studentUser_id);

        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            if(request.getBatch_no() != 0){
                student.setBatch_no(request.getBatch_no());
            }
            if(request.getDepartment_name() != null){
                student.setDepartment_name(request.getDepartment_name());
            }
            if(request.getStudent_id() != null){
                student.setStudent_id(request.getStudent_id());
            }
            studentRepository.save(student);

            EMSUser emsUser = student.getEmsUser();
            if(request.getName() != null){
                emsUser.setName(request.getName());
            }
            if(request.getPhone() != null){
                emsUser.setPhone(request.getPhone());
            }

            emsUserRepository.save(emsUser);

            return "Student User Updated";

        }
        else{
            return "Student is not Updated";
        }

    }

    public List<AllStudentWithAdvisorResponse> findAllStudentWithAdvisor(UUID teacher_user_id){
        List<Student> students =  studentRepository.findAllByAdvisor_EmsUser_Id(teacher_user_id);
        return students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AllStudentWithAdvisorResponse convertToDTO(Student student){
        AllStudentWithAdvisorResponse dto = new AllStudentWithAdvisorResponse();
        dto.setStudent_id(student.getStudent_id());
        dto.setDepartment_name(student.getDepartment_name());
        dto.setBatch_no(student.getBatch_no());

        EMSUser user = student.getEmsUser();
        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public String removeStudentFromAdviceList(UUID student_user_id){
        Optional<Student> optionalStudent = studentRepository.findStudentByUserId(student_user_id);
        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            student.setAdvisor(null);
            studentRepository.save(student);
            return "Student is removed from Advice List";
        }
        else{
            return "Can't be removed from your list";
        }
    }

    public List<AllStudentWithAdvisorResponse> findAllStudentWhoRequest(UUID teacher_user_id){
        List<Student> students = studentRepository.findAllStudentWhoRequestTeacher(teacher_user_id);
        return students.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
