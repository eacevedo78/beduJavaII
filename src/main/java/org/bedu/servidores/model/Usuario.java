package org.bedu.servidores.model;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

import java.util.Date;
import java.util.Set;

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

    /*Relaciones con otras entidades*/
    @OneToMany
    @JoinColumn(name="usuario_id")
    Set<Credencial> credenciales;

    /*Campos de control de creacion y actualizacion*/
    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
