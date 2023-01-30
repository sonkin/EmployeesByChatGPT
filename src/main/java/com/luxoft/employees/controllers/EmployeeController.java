package com.luxoft.employees.controllers;

import com.luxoft.employees.dto.EmployeeDto;
import com.luxoft.employees.services.EmployeeService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        employeeDto.setId(id);
        EmployeeDto updatedEmployee = employeeService.updateEmployee(employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        EmployeeDto employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(bindingResult.getAllErrors().toString());
        }

        EmployeeDto createdEmployee = employeeService.createEmployee(employeeDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment(createdEmployee.getId().toString())
                .build().toUri();
        return ResponseEntity.created(location).body(createdEmployee);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployeePartial(@PathVariable Long id, @Valid @RequestBody Map<String, Object> updates, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException(bindingResult.getAllErrors().toString());
        }

        EmployeeDto employee = employeeService.getEmployeeById(id);

        if (updates.containsKey("name")) {
            employee.setName((String) updates.get("name"));
        }

        if (updates.containsKey("department")) {
            employee.setDepartment((String) updates.get("department"));
        }

        if (updates.containsKey("email")) {
            employee.setEmail((String) updates.get("email"));
        }

        if (updates.containsKey("manager") && updates.get("manager") != null) {
            Map<String, Object> manager = (Map<String, Object>) updates.get("manager");
            Long managerId = Long.valueOf(manager.get("id").toString());
            EmployeeDto managerEmployee = employeeService.getEmployeeById(managerId);
            employee.setManager(managerEmployee);
        }

        EmployeeDto updatedEmployee = employeeService.updateEmployee(employee);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment(updatedEmployee.getId().toString())
                .build().toUri();
        return ResponseEntity.ok().location(location).body(employee);
    }

}
