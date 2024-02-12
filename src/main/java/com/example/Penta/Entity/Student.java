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
    @JoinColumn(name = "advisor_id")
    private EMSUser advisor;

}
