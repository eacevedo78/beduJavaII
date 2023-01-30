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

    //Consulta una aplicacion de un servidor
    @GetMapping("/servidor/{servidorId}/aplicacion/{id}")
    public ResponseEntity<Aplicacion> consultarAplicacion(@PathVariable Long servidorId,
                                                          @PathVariable Long id){
        Aplicacion aplicacion = aplicacionRepository.findById(id).orElseGet(()->null);
        if(aplicacion == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La aplicacion no existe");
        return ResponseEntity.ok(aplicacion);
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
    public ResponseEntity<Aplicacion> modificarAplicacion(@PathVariable Long servidorId,
                                                          @PathVariable Long id,
                                                      @Valid @RequestBody Aplicacion aplicacion ){
        Aplicacion app = aplicacionRepository.findById(id).orElseGet(()->null);
        Servidor serv = servidorRepository.findById(servidorId).orElseGet(() -> null);
        if(app == null || serv == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La aplicacion o servidor no existe");
        //aplicacion.setServidor(serv);
        app.setNombre(aplicacion.getNombre());
        app.setVersion(aplicacion.getVersion());
        app = aplicacionRepository.save(app);
        return ResponseEntity.ok(app);
    }


    @DeleteMapping("/servidor/{servidorId}/aplicacion/{id}")
    public ResponseEntity<Long> borrarAplicacion(@PathVariable Long id){
        Aplicacion app = aplicacionRepository.findById(id).orElseGet(() -> null);

        if(app == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La aplicacion no existe");
        else if(app !=null && app.getCredenciales().size() > 0)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"La aplicacion tiene credenciales asignadas");
        else
            aplicacionRepository.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
