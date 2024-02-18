package com.example.Penta.Service.DesignPattern;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Teacher;

public interface TeacherFactory {
    Teacher createTeacher(EMSUser emsUser, String faculty_name, String designation);
}
