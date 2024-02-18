package com.example.Penta.Service.DesignPattern;

import com.example.Penta.Entity.EMSUser;
import com.example.Penta.Entity.Student;
import com.example.Penta.Entity.Teacher;

public class StudentBuilder {
    private EMSUser emsUser;
    private String department_name;
    private String student_id;
    private int batch_no;
    private Teacher advisor;

    public StudentBuilder setEmsUser(EMSUser emsUser){
        this.emsUser = emsUser;
        return this;
    }
    public StudentBuilder setDepartmentName(String department_name){
        this.department_name = department_name;
        return this;
    }

    public StudentBuilder setStudentID(String student_id){
        this.student_id = student_id;
        return this;
    }
    public StudentBuilder setBatchNo(int batch_no){
        this.batch_no = batch_no;
        return this;
    }
    public StudentBuilder setAdvisor(Teacher advisor){
        this.advisor = advisor;
        return this;
    }

    public Student getStudent(){
        return new Student(emsUser,department_name,student_id,batch_no,advisor);
    }

}
