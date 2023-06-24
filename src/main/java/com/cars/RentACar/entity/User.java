package com.cars.RentACar.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID userId;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String personalNumber;
    private String image;
    private boolean admin;
    private String role;
    @OneToMany(mappedBy = "user")
    private List<Contract> contracts;
}
