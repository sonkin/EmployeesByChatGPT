package com.luxoft.employees.cucumber;

import com.luxoft.employees.dto.EmployeeDto;
import feign.FeignException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class EmployeeSteps {
    private EmployeeDto employeeDto;
    private EmployeeDto createdEmployeeDto;
    private EmployeeDto updatedEmployeeDto;

    @Autowired
    private EmployeeClient employeeClient;

    @Given("employee with id {long}, name {string}, department {string}, email {string}")
    public void setEmployeeDto(Long id, String name, String department, String email) {
        employeeDto = new EmployeeDto();
        employeeDto.setId(id);
        employeeDto.setName(name);
        employeeDto.setDepartment(department);
        employeeDto.setEmail(email);
    }

    @Given("employee with id {long}")
    public void setEmployeeDto(Long id) {
        employeeDto = new EmployeeDto();
        employeeDto.setId(id);
    }

    @When("create employee")
    public void createEmployee() {
        createdEmployeeDto = employeeClient.createEmployee(employeeDto).getBody();
    }

    @Then("employee with name {string} should be created")
    public void checkEmployeeCreated(String name) {
        assertThat(createdEmployeeDto.getName()).isEqualTo(name);
    }

    @When("get JSON with employee details")
    public void getEmployee() {
        employeeDto = employeeClient.getEmployee(employeeDto.getId());
    }

    @Then("employee with name {string} should be returned")
    public void checkEmployeeReturned(String name) {
        assertThat(employeeDto.getName()).isEqualTo(name);
    }

    @When("update employee with id {long} name to {string}")
    public void updateEmployee(Long id, String name) {
        updatedEmployeeDto = employeeClient.updateEmployee(id,
                EmployeeDto.builder().id(id).name(name).build()).getBody();
    }

    @Then("employee with id {long} should have name {string}")
    public void checkEmployeeUpdated(Long id, String name) {
        employeeDto = employeeClient.getEmployee(id);
        assertThat(employeeDto.getName()).isEqualTo(name);
    }

    @When("delete employee with id {long}")
    public void deleteEmployee(Long id) {
        employeeClient.deleteEmployee(id);
    }

    @Then("employee with id {long} should be deleted")
    public void checkEmployeeDeleted(Long id) {
        try {
            employeeClient.getEmployee(id);
            fail("Employee with id " + id + " should be deleted");
        } catch (FeignException e) {
            assertThat(e.status()).isEqualTo(404);
        }
    }
}
