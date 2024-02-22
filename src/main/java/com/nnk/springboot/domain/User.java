package com.nnk.springboot.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    
    @NotBlank(message = "Username is mandatory")
    @Column(name=" username")
    private String username;
    
    @NotBlank(message = "Password is mandatory")
    @Column(name=" password")
    private String password;
    
    @NotBlank(message = "FullName is mandatory")
    @Column(name=" fullname")
    private String fullname;
    
    @NotBlank(message = "Role is mandatory")
    @Column(name=" role")
    private String role;
}
