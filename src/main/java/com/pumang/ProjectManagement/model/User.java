package com.pumang.ProjectManagement.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fullName;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee", cascade=CascadeType.ALL)
    private List<Issue>assignedIssues=new ArrayList<>();

    private int projectSize;


}
