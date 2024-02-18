package com.example.Penta.Service.DesignPattern;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Student;
import com.example.Penta.Entity.Teacher;

public class StudentFactoryImpl implements StudentFactory{

    @Override
    public Student createStudent(EMSUser emsUser, String department_name, String student_id, int batch_no, Teacher advisor) {
        Student student = new StudentBuilder()
                .setEmsUser(emsUser)
                .setDepartmentName(department_name)
                .setStudentID(student_id)
                .setBatchNo(batch_no)
                .setAdvisor(advisor)
                .getStudent();

        return student;
    }
}
