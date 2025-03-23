package com.dh.DentalClinicMVC.controller;

import com.dh.DentalClinicMVC.entity.Dentist;
import com.dh.DentalClinicMVC.service.IDentistService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//import static org.springframework.test.web.servlet.request.result.MockMvcResultHandler.*;
//import static org.springframework.test.web.servlet.request.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

//import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
//AutoConfigureMockMvc configura el entorno servlet de pruebas para el controlador
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DentistControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IDentistService dentistService;

   // @BeforeEach
    public void dataLoad(){

        Dentist dentist= new Dentist();
        dentist.setRegistration(1234);
        dentist.setName("Manuelito");
        dentist.setLastName("Perez");
        dentistService.save(dentist);
//        Dentist dentist2= new Dentist();
//        dentist2.setRegistration(5678);
//        dentist2.setName("Pepito");
//        dentist2.setLastName("Perez");
//        dentistService.save(dentist2);
    }

    @Test
    @Order(2)
    void testGetDentistById() throws Exception {
        dataLoad();
        mockMvc.perform(get("/odontologos/1")
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
                .andExpect(jsonPath("$.registration").value("1234"))
                .andExpect(jsonPath("$.name").value("Manuelito"))
                .andExpect(jsonPath("$.lastName").value("Perez"));

    }

    @Test
    @Order(3)
void testPostDentist() throws Exception {

        String dentistSave = "{\"registration\": 12345, \"name\": \"Magnolia\", \"lastName\": \"Baez\"}";
        mockMvc.perform(post("/odontologos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dentistSave)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registration").value("12345"))
                .andExpect(jsonPath("$.name").value("Magnolia"))
                .andExpect(jsonPath("$.lastName").value("Baez"));
    }

    // De esta manera funciona cuando esta vacio o iniciando el servicio ya que si
    // contiene odontologos en la Db no andaria

@Test
@Order(1)
    void testGetAllDentists() throws Exception {

        mockMvc.perform(get("/odontologos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

}