package com.example.Penta.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private EMSUser emsUser;

    private String department_name;
    private String student_id;
    private int batch_no;

    @ManyToOne
    @JoinColumn(name = "advisor_id",referencedColumnName = "user_id")
    private Teacher advisor;

    public Student(EMSUser emsUser, String departmentName, String studentId, int batchNo, Teacher advisor) {
        this.emsUser = emsUser;
        this.student_id = studentId;
        this.department_name = departmentName;
        this.batch_no = batchNo;
        this.advisor = advisor;
    }
}
