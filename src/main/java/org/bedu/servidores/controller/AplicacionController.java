package org.bedu.servidores.controller;

import jakarta.validation.Valid;
import org.bedu.servidores.model.Aplicacion;
import org.bedu.servidores.model.Servidor;
import org.bedu.servidores.repos.AplicacionRepository;
import org.bedu.servidores.repos.ServidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
public class AplicacionController {
    @Autowired
    private AplicacionRepository aplicacionRepository;
    @Autowired
    private ServidorRepository servidorRepository;

    //Consulta todas las aplicaciones que hay en la BD
    @GetMapping("/aplicaciones")
    public ResponseEntity<List<Aplicacion>> consultarAplicaciones(){
        List<Aplicacion> aplicaciones = aplicacionRepository.findAll();
        return ResponseEntity.ok(aplicaciones);
    }

    //Consulta todas las aplicaciones de un servidor dado
    @GetMapping("/servidor/{servidorId}/aplicaciones")
    public ResponseEntity<List<Aplicacion>> consultarAplicaciones(@PathVariable Long servidorId){
        List<Aplicacion> aplicaciones = aplicacionRepository.findByServidor_Id(servidorId);
        return ResponseEntity.ok(aplicaciones);
    }

    //Crea una nueva aplicacion en un servidor
    @PostMapping("/servidor/{servidorId}/aplicacion")
    public ResponseEntity<Aplicacion> crearAplicacion(@PathVariable Long servidorId,
                                                      @Valid @RequestBody Aplicacion aplicacion){
        Servidor serv = servidorRepository.findById(servidorId).orElseGet(() -> null);
        if(serv != null ) {
            aplicacion.setServidor(serv);
            Aplicacion apps = aplicacionRepository.save(aplicacion);
            return ResponseEntity.ok(apps);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El servidor no existe");
        }
    }

    //Actualiza una nueva aplicacion en un servidor
    @PutMapping("/servidor/{servidorId}/aplicacion/{id}")
    public ResponseEntity<Aplicacion> modificarAplicacion(@PathVariable Long id,
                                                      @Valid @RequestBody Aplicacion aplicacion ){
        Aplicacion app = aplicacionRepository.save(aplicacion);
        return ResponseEntity.ok(app);
    }


    @DeleteMapping("/servidor/{servidorId}/aplicacion/{id}")
    public ResponseEntity<Long> borrarAplicacion(@PathVariable Long id){
        Aplicacion app = aplicacionRepository.findById(id).orElseGet(() -> null);
        if(app !=null )
            aplicacionRepository.deleteById(id);
        else
            return ResponseEntity.notFound().build();
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
