package com.dassonville.quizine.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "users")
public class User {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Veuillez saisir un nom d''utilisateur.")
    @Size(min = 2, max = 50, message = "Veuillez saisir un nom d''utilisateur entre 4 et 50 caractères.")
    private String username;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Veuillez saisir un email.")
    @Email(message = "Veuillez saisir un email valide.")
    private String email;

    @Column(nullable = false)
    @NotEmpty(message = "Veuillez saisir un mot de passe.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$!?%^&+=]).{8,}$",
            message = "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial (@#$!?%^&+=)"
    )
    private String password;

    @Column(nullable = false)
    private Boolean public_profile;

    @Column(name = "registered_at", insertable = false, updatable = false)
    private LocalDateTime registeredAt;

    @Column(name = "validated_at")
    private LocalDateTime validatedAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "connected_at")
    private LocalDateTime connectedAt;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_role", referencedColumnName = "id", nullable = false)
    )
    private Set<Role> roles;
}
