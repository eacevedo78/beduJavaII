package org.bedu.servidores.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.rest.core.annotation.RestResource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.ArrayList;


import java.util.Date;

@Entity
@Data
@RestResource(rel="servidores", path="servidor")
public class Servidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message="La IP es obligatoria")
    @Pattern(regexp = "^([0-1]?\\d?\\d|2[0-4]\\d|25[0-5])(\\.([0-1]?\\d?\\d|2[0-4]\\d|25[0-5])){3}$",
            message = "Formato de IP no valido")
    @Size(min = 7, max = 15, message = "La IP debe tener entre 5 y 30 caracteres")
    private String ip;

    @Column
    @NotBlank(message="El nombre el obligatorio")
    @Size(min = 5, max = 30, message = "El nombre debe tener entre 5 y 30 letras")
    private String nombre;

    @Column
    @NotBlank(message="La descripcion es obligatoria")
    @Size(min = 10, max = 50, message = "La descripcion debe tener entre 5 y 30 letras")
    private String descripcion;

    /*Relaciones con otras entidades*/
    @OneToMany
    @JsonIgnore
    @JoinColumn(name="servidor_id")
    private List<Aplicacion> aplicaciones =  new ArrayList<>();

    /*Campos de control de creacion y actualizacion*/
    @Column(name="created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name="updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
