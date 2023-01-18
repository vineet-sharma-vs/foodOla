package com.group11.fooddelivery.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "UserInfo")
@Getter
@Setter

public class User {

    @Id
    @Email(message = "Please Enter Valid Email Address!!")
    private String email;
    @NotEmpty(message = "Name Should be non-empty!!")
    private String name;
    @NotEmpty(message = "Password Should be non-empty!!")
    private String password;
    @NotEmpty(message = "Role should be non-empty")
    private String role;
    private String salt;
    private String token;
    private boolean isActive = true; //if false, then user is banned.
}
