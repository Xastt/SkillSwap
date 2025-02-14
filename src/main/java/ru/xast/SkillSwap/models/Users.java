package ru.xast.SkillSwap.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "username")
    @Size(min = 2,max = 100, message = "Size should be between 2 and 100!")
    @NotEmpty(message = "Enter your name!")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PersInf personalInfo;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ProfInf professionalInfo;

    public Users(){}

    public Users(String username){
        this.username = username;
    }
}
