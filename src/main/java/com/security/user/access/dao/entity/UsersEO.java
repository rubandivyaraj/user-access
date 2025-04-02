package com.security.user.access.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@Validated
public class UsersEO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotEmpty(message = "username should not be empty")
    private String username;

    @Column(name = "password_hash")
    @NotEmpty(message = "password should not be empty")
    private String password;

    @NotEmpty(message = "email should not be empty")
    @Email(message = "Invalid email format")
    private String email;
    private String role;
    private boolean isEnabled;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
