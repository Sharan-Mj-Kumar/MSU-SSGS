package com.example.SSGS.Tables;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "users")
public class Users {

    @Id
    private Integer staff_id;

    private String name;
    private String email;
    private String password;
    private String category;
    private String designation;
    private String dept_section;
    private LocalDate date_joined;
    private Boolean is_admin;

}
