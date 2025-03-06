package com.dassonville.api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Veuillez saisir un nom de catégorie.")
    @Size(max = 70, message = "Le nom de la catégorie ne doit pas dépasser 70 caractères.")
    private String name;

    @Size(max = 250, message = "La description ne doit pas dépasser 250 caractères.")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_theme", referencedColumnName = "id", nullable = false)
    private Theme theme;
}
