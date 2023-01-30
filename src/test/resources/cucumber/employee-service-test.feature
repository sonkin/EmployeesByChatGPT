Feature: Employee REST service
  As a user
  I want to be able to manage employees via REST API

  Scenario: Create employee
    Given employee with id 1, name "John Doe", department "IT", email "john.doe@example.com"
    When create employee
    Then employee with name "John Doe" should be created

  Scenario: Get employee
    Given employee with id 1
    When get JSON with employee details
    Then employee with name "John Doe" should be returned

  Scenario: Update employee
    When update employee with id 1 name to "Jane Doe"
    Then employee with id 1 should have name "Jane Doe"

  Scenario: Delete employee
    When delete employee with id 1
    Then employee with id 1 should be deleted


