package org.bedu.servidores.controller;

import org.bedu.servidores.model.Aplicacion;
import org.bedu.servidores.model.Credencial;
import org.bedu.servidores.model.Usuario;
import org.bedu.servidores.repos.AplicacionRepository;
import org.bedu.servidores.repos.CredencialRepository;
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


@WebMvcTest(CredencialController.class)
@AutoConfigureMockMvc(addFilters = false)
class CredencialControllerTest {
    @MockBean
    private CredencialRepository cr;
    @MockBean
    private UsuarioRepository ur;
    @MockBean
    private AplicacionRepository ar;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void consultarCredenciales() throws Exception{
        Long id=1l;
        Credencial a = new Credencial();
        a.setId(id);
        when(ur.findById(id)).thenReturn(Optional.of(new Usuario()));
        mockMvc.perform(get("/usuario/1/credenciales"))
                .andExpect(status().isOk());
    }

    @Test
    void consultarCredencial() throws Exception {
        long id = 1L;
        Credencial s = new Credencial();
        s.setId(id);
        s.setLogin("administrador");
        s.setPassword("123456");
        when(ur.findById(id)).thenReturn(Optional.of(new Usuario()));
        when(cr.findById(id)).thenReturn(Optional.of(s));
        mockMvc.perform(get("/usuario/1/credencial/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.login").value(s.getLogin()))
                .andDo(print());
    }

    @Test
    void crearCredencial() throws Exception {
        Credencial s = new Credencial();
        s.setId(1l);
        s.setLogin("administrador");
        s.setPassword("123456");
        when(ur.findById(1l)).thenReturn(Optional.of(new Usuario()));
        when(ar.findById(1l)).thenReturn(Optional.of(new Aplicacion()));
        mockMvc.perform(post("/usuario/1/aplicacion/1/credencial").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void actualzarCredencial() throws Exception {
        long id = 1L;
        Credencial s = new Credencial();
        s.setId(id);
        s.setLogin("administrador");
        s.setPassword("123456");
        when(cr.findById(id)).thenReturn(Optional.of(s));
        when(ur.findById(1l)).thenReturn(Optional.of(new Usuario()));
        when(ar.findById(1l)).thenReturn(Optional.of(new Aplicacion()));
        mockMvc.perform(put("/usuario/1/aplicacion/1/credencial/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void eliminarCredencial() throws Exception {
        long id = 1L;
        Credencial s = new Credencial();
        s.setId(id);
        s.setLogin("administrador");
        s.setPassword("123456");
        //when(sr.findById(id)).thenReturn(Optional.of(s));
        doNothing().when(cr).deleteById(id);
        mockMvc.perform(delete("/usuario/credencial/{id}", id).header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJub21icmUiOiJlZGlhekBnbWFpbC5jb20iLCJyb2wiOiJBRE0ifQ.5qZp1oMIiH4AJtod2K8ClOnGpzvaSptFe-deiZ1NGWI"))
                .andExpect(status().isNotFound())
                .andDo(print());
        /*mockMvc.perform(get("/usuario/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());*/
    }
}