package com.luxoft.employees;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.employees.controllers.EmployeeController;
import com.luxoft.employees.dto.EmployeeDto;
import com.luxoft.employees.services.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void testGetEmployee() throws Exception {
        EmployeeDto employee = EmployeeDto.builder()
                .id(1L).name("John").email("john@example.com").build();
        given(employeeService.getEmployeeById(1L)).willReturn(employee);

        mvc.perform(get("/employees/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.email", is("john@example.com")));
    }

    @Test
    public void testCreateEmployee() throws Exception {
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L)
                .name("John")
                .email("john@example.com")
                .build();
        given(employeeService.createEmployee(employeeDto)).willReturn(employeeDto);

        mvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/employees/1")));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        EmployeeDto manager = EmployeeDto.builder()
                .id(2L)
                .build();
        EmployeeDto employeeDto = EmployeeDto.builder()
                .id(1L).name("John")
                .email("john@example.com")
                .manager(manager)
                .build();
        given(employeeService.updateEmployee(employeeDto)).willReturn(employeeDto);
        given(employeeService.getEmployeeById(1L)).willReturn(employeeDto);

        mvc.perform(patch("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(employeeDto)))
                .andExpect(header().string("location", containsString("/employees/1")))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
