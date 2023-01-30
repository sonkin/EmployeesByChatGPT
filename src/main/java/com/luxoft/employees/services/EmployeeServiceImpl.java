package com.luxoft.employees.services;

import com.luxoft.employees.dto.EmployeeDto;
import com.luxoft.employees.dto.EmployeeMapper;
import com.luxoft.employees.model.Employee;
import com.luxoft.employees.repositories.EmployeeRepository;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEmployee(employeeDto);
        employee = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEmployee(employeeDto);
        employee = employeeRepository.save(employee);
        return employeeMapper.toEmployeeDto(employee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employeeMapper.toEmployeeDto(employee.get());
        } else {
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        }
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employeeMapper.toEmployeeDtoList(employees);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}