package com.example.Penta.Service.DesignPattern;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Teacher;

public class TeacherBuilder {
    private EMSUser emsUser;
    private String faculty_name;
    private String designation;

    public TeacherBuilder setEmsUser(EMSUser emsUser){
        this.emsUser = emsUser;
        return this;
    }

    public TeacherBuilder setFacultyName(String faculty_name){
        this.faculty_name = faculty_name;
        return this;
    }

    public TeacherBuilder setDesignation(String designation){
        this.designation = designation;
        return this;
    }

    public Teacher getTeacher(){
        return new Teacher(0,emsUser,faculty_name,designation);
    }
}
