package com.dassonville.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @Size(max = 50, message = "Le nom du rôle ne doit pas dépasser 50 caractères.")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
