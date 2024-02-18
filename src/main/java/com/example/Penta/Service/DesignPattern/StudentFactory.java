package com.example.Penta.Service.DesignPattern;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Student;
import com.example.Penta.Entity.Teacher;

public interface StudentFactory {
    Student createStudent(EMSUser emsUser, String department_name, String student_id, int batch_no, Teacher advisor);
}
