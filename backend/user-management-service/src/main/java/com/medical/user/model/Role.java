package com.medical.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
    
    public Role(ERole name) {
        this.name = name;
    }
    
    public static enum ERole {
        ROLE_PATIENT,
        ROLE_DOCTOR,
        ROLE_ADMIN
    }
} 