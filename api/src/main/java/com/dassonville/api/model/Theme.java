package com.dassonville.api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "themes")
@Data
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Veuillez saisir un nom de thème.")
    @Size(max = 50, message = "Le nom du thème ne doit pas dépasser 70 caractères.")
    private String name;

    @Size(max = 250, message = "La description ne doit pas dépasser 250 caractères.")
    private String description;
}
