package org.bedu.servidores.controller;

import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.bedu.servidores.model.Aplicacion;
import org.bedu.servidores.model.Credencial;
import org.bedu.servidores.model.Usuario;
import org.bedu.servidores.repos.AplicacionRepository;
import org.bedu.servidores.repos.CredencialRepository;
import org.bedu.servidores.repos.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CredencialController {
    @Autowired
    private CredencialRepository credencialRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AplicacionRepository aplicacionRepository;

    //Consulta todas las credenciales
    @GetMapping("/credenciales")
    public ResponseEntity<List<Credencial>> consultarCredenciales(){
        List<Credencial> credenciales = credencialRepository.findAll();
        return ResponseEntity.ok(credenciales);
    }

    //Consulta todas las credenciales de un usuario
    @GetMapping("/usuario/{usuarioId}/credenciales")
    public ResponseEntity<List<Credencial>> consultarCredenciales(@PathVariable Long usuarioId){
        List<Credencial> credenciales = credencialRepository.findByUsuario_Id(usuarioId);
        return ResponseEntity.ok(credenciales);
    }

    //Consulta una credencial de un usuario
    @GetMapping("/usuario/{usuarioId}/credencial/{id}")
    public ResponseEntity<Credencial> consultarCredencial(@PathVariable Long usuarioId,
                                                                  @PathVariable Long id){
        Credencial credencial = credencialRepository.findById(id).orElseGet(()->null);
        if(credencial == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La credencial no existe");
        return ResponseEntity.ok(credencial);
    }

    //Crea una nueva credencial
    @PostMapping("/usuario/{usuarioId}/aplicacion/{aplicacionId}/credencial")
    public ResponseEntity<Credencial> crearCredencial(@PathVariable Long usuarioId,
                                                      @PathVariable Long aplicacionId,
                                                      @Valid @RequestBody Credencial credencial){
        Usuario usu = usuarioRepository.findById(usuarioId).orElseGet(()->null);
        Aplicacion app = aplicacionRepository.findById(aplicacionId).orElseGet(()->null);
        if(usu != null && app != null) {
            credencial.setAplicacion(app);
            credencial.setUsuario(usu);
            Credencial cred = credencialRepository.save(credencial);
            return ResponseEntity.ok(cred);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La aplicacion o usuario no existen");
        }
    }

    @PutMapping("/usuario/{usuarioId}/aplicacion/{aplicacionId}/credencial/{id}")
    public ResponseEntity<Credencial> modificarCredencial(@PathVariable Long usuarioId,
                                                          @PathVariable Long aplicacionId,
                                                      @PathVariable Long id,
                                                      @Valid @RequestBody Credencial credencial ){
        Credencial cred = credencialRepository.findById(id).orElseGet(()->null);
        Usuario usu = usuarioRepository.findById(usuarioId).orElseGet(()->null);
        Aplicacion app = aplicacionRepository.findById(aplicacionId).orElseGet(()->null);
        if(usu != null && app != null && cred != null) {
            cred.setAplicacion(app);
            //System.out.println(app.getNombre());
            /*credencial.setUsuario(usu);*/
            cred.setLogin(credencial.getLogin());
            cred.setPassword(credencial.getPassword());
            cred = credencialRepository.save(cred);
            return ResponseEntity.ok(cred);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"La aplicacion, usuario o credencial no existen");
        }
    }

    @DeleteMapping("/usuario/{usuarioId}/credencial/{id}")
    public ResponseEntity<Long> borrarCredencial(@PathVariable Long usuarioId,
                                                 @PathVariable Long id){
        Credencial cred = credencialRepository.findById(id).orElseGet(() -> null);
        Usuario usu = usuarioRepository.findById(usuarioId).orElseGet(()->null);
        if(cred !=null && usu != null)
            credencialRepository.deleteById(id);
        else
            return ResponseEntity.notFound().build();
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
