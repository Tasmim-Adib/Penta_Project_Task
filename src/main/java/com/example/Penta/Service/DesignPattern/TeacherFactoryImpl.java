package com.example.Penta.Service.DesignPattern;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Teacher;

public class TeacherFactoryImpl implements TeacherFactory{
    @Override
    public Teacher createTeacher(EMSUser emsUser, String faculty_name, String designation) {
        Teacher teacher = new TeacherBuilder()
                .setEmsUser(emsUser)
                .setDesignation(designation)
                .setFacultyName(faculty_name)
                .getTeacher();

        return teacher;
    }
}
