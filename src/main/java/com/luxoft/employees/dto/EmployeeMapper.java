package com.luxoft.employees.dto;

import com.luxoft.employees.model.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    EmployeeDto toEmployeeDto(Employee employee);
    Employee toEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> toEmployeeDtoList(List<Employee> employees);
}