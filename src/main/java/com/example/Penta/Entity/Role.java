package com.example.Penta.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role_id;

    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;
    private String description;

    @OneToMany(mappedBy = "role",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<EMSUser> user = new ArrayList<>();

    public Role(int roleId, RoleEnum roleEnum, String description) {
        this.role_id = roleId;
        this.roleEnum = roleEnum;
        this.description = description;
    }
}
