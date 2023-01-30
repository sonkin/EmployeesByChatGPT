package com.luxoft.employees.cucumber;

import com.luxoft.employees.dto.EmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "employee", url = "http://localhost:8080")
public interface EmployeeClient {

    @GetMapping("/employees/{id}")
    EmployeeDto getEmployee(@PathVariable("id") Long id);

    @PostMapping("/employees")
    ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employee);

    @PatchMapping("/employees/{id}")
    ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("id") Long id, @RequestBody EmployeeDto employee);

    @DeleteMapping("/employees/{id}")
    ResponseEntity<Void> deleteEmployee(@PathVariable("id") Long id);
}
