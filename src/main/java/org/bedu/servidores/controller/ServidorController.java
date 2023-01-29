package org.bedu.servidores.controller;

import jakarta.validation.Valid;
import org.bedu.servidores.model.Servidor;
import org.bedu.servidores.repos.ServidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ServidorController {
    @Autowired
    private ServidorRepository servidorRepository;

    //Consulta todos los servidores
    @GetMapping("/servidores")
    public ResponseEntity<List<Servidor>> consultarServidores(){
        List<Servidor> servidores = servidorRepository.findAll();
        return ResponseEntity.ok(servidores);
    }

    //Consulta un servidor indicado por el id
    @GetMapping("/servidor/{id}")
    public ResponseEntity<Servidor> crearServidor(@PathVariable Long id){
        Servidor serv = servidorRepository.findById(id).orElseGet(()-> null);
        if(serv == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"El servidor no existe");
        return ResponseEntity.ok(serv);
    }

    //Crea un nuevo servidor
    @PostMapping("/servidor")
    public ResponseEntity<Servidor> crearServidor(@Valid @RequestBody Servidor servidor){
        Servidor serv = servidorRepository.save(servidor);
        return ResponseEntity.ok(serv);
    }

    //Actualiza un servidor
    @PutMapping("/servidor/{id}")
    public ResponseEntity<Servidor> modificarServidor(@PathVariable Long id,
                                                              @Valid @RequestBody Servidor servidor ){
        Servidor serv = servidorRepository.save(servidor);
        return ResponseEntity.ok(serv);
    }

    //Elimina un servidor
    @DeleteMapping("/servidor/{id}")
    public ResponseEntity<Long> borrarServidor(@PathVariable Long id){
        Servidor serv = servidorRepository.findById(id).orElseGet(() -> null);

        if(serv ==null )
            return ResponseEntity.notFound().build();
        else if(serv != null && serv.getAplicaciones().size() > 0)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"El servidor tiene aplicaciones asociadas");
        else
            servidorRepository.deleteById(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
