package com.luxoft.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private Long id;
    @NotBlank
    private String name;
    private String department;
    @NotBlank
    private String email;
    private EmployeeDto manager;
}
