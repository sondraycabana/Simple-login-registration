package com.kb.simpleloginregistration.data.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    private Collection<Role> roles;

    public User() {
    }


}