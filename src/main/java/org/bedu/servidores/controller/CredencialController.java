package org.bedu.servidores.controller;

import jakarta.validation.Valid;
import org.bedu.servidores.model.Credencial;
import org.bedu.servidores.repos.CredencialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CredencialController {
    @Autowired
    private CredencialRepository credencialRepository;

    @GetMapping("/credencial")
    public ResponseEntity<List<Credencial>> consultarCredenciales(){
        List<Credencial> credenciales = credencialRepository.findAll();
        return ResponseEntity.ok(credenciales);
    }
    @PostMapping("/credencial")
    public ResponseEntity<Credencial> crearCredencial(@Valid @RequestBody Credencial credencial){
        Credencial cred = credencialRepository.save(credencial);
        return ResponseEntity.ok(cred);
    }

    @PutMapping("/credencial/{id}")
    public ResponseEntity<Credencial> modificarCredencial(@PathVariable Long id,
                                                      @Valid @RequestBody Credencial credencial ){
        Credencial cred = credencialRepository.save(credencial);
        return ResponseEntity.ok(cred);
    }

    @DeleteMapping("/credencial/{id}")
    public ResponseEntity<Long> borrarCredencial(@PathVariable Long id){
        Credencial cred = credencialRepository.findById(id).orElseGet(() -> null);
        if(cred !=null )
            credencialRepository.deleteById(id);
        else
            return ResponseEntity.notFound().build();
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
