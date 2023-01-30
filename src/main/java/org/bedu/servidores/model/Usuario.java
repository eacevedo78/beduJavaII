package org.bedu.servidores.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@RestResource(rel="usuarios", path="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message="El nombre de usuario es obligatorio")
    @Size(min = 5, max = 30, message = "El nombre debe tener entre 5 y 30 letras")
    private String nombre;

    @Column
    @NotBlank(message="El correo electronico es obligatorio")
    @Email(message = "Formato de correo electronico invalido")
    private String correo;

    @Column
    @NotBlank(message="El password es obligatorio")
    @Size(min = 5, message = "El password debe tener 5 caracteres minimo.")
    private String password;

    @Column
    @NotBlank(message="El rol de usuario es obligatorio")
    @Pattern(regexp = "ADM|USU", message = "El rol debe ser 'ADM' o 'USU'")
    private String rol;

    /*Relaciones con otras entidades*/
    @OneToMany
    @JsonIgnore
    @JoinColumn(name="usuario_id")
    private List<Credencial> credenciales = new ArrayList<>();

    /*Campos de control de creacion y actualizacion*/
    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
