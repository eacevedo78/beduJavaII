package org.bedu.servidores.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@RestResource(rel="aplicaciones", path="aplicacion")
public class Aplicacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message="El nombre el obligatorio")
    @Size(min = 5, max = 30, message = "El nombre debe tener entre 5 y 30 letras")
    private String nombre;

    @Column
    @NotBlank(message="La version es obligatoria")
    @Size(min = 2, max = 20, message = "La version debe tener entre 2 y 20 caracteres")
    private String version;

    /*Relaciones con otras entidades*/
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="servidor_id",referencedColumnName = "id",nullable = false)
    private Servidor servidor;

    @OneToMany
    @JsonIgnore
    @JoinColumn(name="aplicacion_id")
    private List<Credencial> credenciales = new ArrayList<>();

    /*Campos de control de creacion y actualizacion*/
    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
