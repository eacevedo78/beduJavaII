package org.bedu.servidores.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Date;

@Entity
@Data
@RestResource(rel="credenciales", path="credencial")
public class Credencial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message="El login es obligatorio")
    @Size(min = 5, max = 20, message = "El login debe tener entre 5 y 20 letras")
    private String login;

    @Column
    @NotBlank(message="El password es obligatorio")
    @Size(min = 5, max = 15, message = "El password debe tener entre 5 y 15 caracteres")
    private String password;

    /*Relaciones con otras entidades*/
    @ManyToOne
    @JoinColumn(name="usuario_id",referencedColumnName = "id",nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="aplicacion_id",referencedColumnName = "id",nullable = false)
    private Aplicacion aplicacion;

    /*Campos de control de creacion y actualizacion*/
    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
