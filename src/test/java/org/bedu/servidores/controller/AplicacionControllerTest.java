package org.bedu.servidores.controller;

import org.bedu.servidores.model.Aplicacion;
import org.bedu.servidores.model.Servidor;
import org.bedu.servidores.repos.AplicacionRepository;
import org.bedu.servidores.repos.ServidorRepository;
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


@WebMvcTest(AplicacionController.class)
@AutoConfigureMockMvc(addFilters = false)
class AplicacionControllerTest {
    @MockBean
    private AplicacionRepository ar;
    @MockBean
    private ServidorRepository sr;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void consultarAplicaciones() throws Exception{
        Long id=1l;
        Aplicacion a = new Aplicacion();
        a.setId(id);
        when(ar.findById(id)).thenReturn(Optional.of(a));
        mockMvc.perform(get("/servidor/1/aplicaciones"))
                .andExpect(status().isOk());
    }

    @Test
    void consultarAplicacion() throws Exception {
        long id = 1L;
        Aplicacion s = new Aplicacion();
        s.setId(id);
        s.setNombre("Aplicacion de prueba");
        s.setVersion("5.5");
        when(ar.findById(id)).thenReturn(Optional.of(s));
        mockMvc.perform(get("/servidor/1/aplicacion/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value(s.getNombre()))
                .andDo(print());
    }

    @Test
    void crearAplicacion() throws Exception {
        Aplicacion s = new Aplicacion();
        s.setId(1l);
        s.setNombre("Aplicacion de prueba");
        s.setVersion("5.5");
        when(sr.findById(1l)).thenReturn(Optional.of(new Servidor()));
        mockMvc.perform(post("/servidor/1/aplicacion").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void actualzarAplicacion() throws Exception {
        long id = 1L;
        Aplicacion s = new Aplicacion();
        s.setId(id);
        s.setNombre("Aplicacion de prueba");
        s.setVersion("5.5");
        when(ar.findById(id)).thenReturn(Optional.of(s));
        when(sr.findById(1l)).thenReturn(Optional.of(new Servidor()));
        mockMvc.perform(put("/servidor/1/aplicacion/1").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void eliminarAplicacion() throws Exception {
        long id = 1L;
        Aplicacion s = new Aplicacion();
        s.setId(id);
        s.setNombre("Aplicacion de prueba");
        s.setVersion("5.5");
        //when(sr.findById(id)).thenReturn(Optional.of(s));
        doNothing().when(ar).deleteById(id);
        mockMvc.perform(delete("/servidor/aplicacion/{id}", id).header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJub21icmUiOiJlZGlhekBnbWFpbC5jb20iLCJyb2wiOiJBRE0ifQ.5qZp1oMIiH4AJtod2K8ClOnGpzvaSptFe-deiZ1NGWI"))
                .andExpect(status().isNotFound())
                .andDo(print());
        /*mockMvc.perform(get("/usuario/{id}", id))
                .andExpect(status().isNotFound())
                .andDo(print());*/
    }
}