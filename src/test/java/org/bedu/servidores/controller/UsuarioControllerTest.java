package org.bedu.servidores.controller;

import org.bedu.servidores.model.Usuario;
import org.bedu.servidores.repos.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;


@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
class UsuarioControllerTest {
    @MockBean
    private UsuarioRepository ur;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void consultarUsuarios() throws Exception{
        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    void consultarUsuario() throws Exception {
        long id = 1L;
        Usuario s = new Usuario();
        s.setId(id);
        s.setNombre("Usuario de prueba");
        s.setPassword("12345");
        s.setRol("ADM");
        s.setCorreo("algo@gmail.com");
        when(ur.findById(id)).thenReturn(Optional.of(s));
        mockMvc.perform(get("/usuario/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value(s.getNombre()))
                .andDo(print());
    }

    @Test
    void crearUsuario() throws Exception {
        Usuario s = new Usuario();
        s.setId(1l);
        s.setNombre("Usuario de prueba");
        s.setPassword("12345");
        s.setRol("ADM");
        s.setCorreo("algo@gmail.com");
        mockMvc.perform(post("/usuario").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void actualzarUsuario() throws Exception {
        long id = 1L;
        Usuario s = new Usuario();
        s.setId(id);
        s.setNombre("Usuario de prueba");
        s.setPassword("12345");
        s.setRol("ADM");
        s.setCorreo("algo@gmail.com");
        when(ur.findById(id)).thenReturn(Optional.of(s));
        mockMvc.perform(put("/usuario/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void eliminarUsuario() throws Exception {
        long id = 1L;
        Usuario s = new Usuario();
        s.setId(id);
        s.setNombre("Usuario de prueba");
        s.setPassword("12345");
        s.setRol("ADM");
        s.setCorreo("algo@gmail.com");
        //when(sr.findById(id)).thenReturn(Optional.of(s));
        doNothing().when(ur).deleteById(id);
        mockMvc.perform(delete("/usuario/{id}", id).header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJub21icmUiOiJlZGlhekBnbWFpbC5jb20iLCJyb2wiOiJBRE0ifQ.5qZp1oMIiH4AJtod2K8ClOnGpzvaSptFe-deiZ1NGWI"))
                .andExpect(status().isNotFound())
                .andDo(print());
        /*mockMvc.perform(get("/usuario/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());*/
    }
}