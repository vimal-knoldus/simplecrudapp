package com.knoldus.simplecrudapp.controller;

import com.knoldus.simplecrudapp.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void init() {

    }

    @Test
    void testGetAllEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/employee")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testPostEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/employee")
                        .content("{\"name\":\"Vimal\",\"age\":30}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
