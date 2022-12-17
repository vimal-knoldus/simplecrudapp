package com.knoldus.simplecrudapp.controller;

import com.knoldus.simplecrudapp.entity.Employee;
import com.knoldus.simplecrudapp.exception.ResourceNotFoundException;
import com.knoldus.simplecrudapp.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@AllArgsConstructor
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeRepository employeeRepository;

    @Operation(description = "Get all the employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all employee",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Employee.class)))}
            )
    })
    @GetMapping
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Operation(description = "Get employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
        log.info("Get employee with id {}", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id "+id));
        return ResponseEntity.ok().body(employee);
    }

    @Operation(description = "Add a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee added",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content)
    })
    @PostMapping
    public Employee addEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @Operation(description = "Update an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployeeById(@PathVariable(value = "id") long id, @RequestBody Employee updatedEmployee) throws ResourceNotFoundException {
        log.info("Updating employee");
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id "+id));
        employee.setName(updatedEmployee.getName());
        employee.setAge(updatedEmployee.getAge());
        employeeRepository.save(employee);
        return ResponseEntity.ok().body(employee);
    }

    @Operation(description = "Delete an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable(value = "id") long id) throws ResourceNotFoundException {
        log.info("Deleting employee");
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empoyee not found with id "+id));
        employeeRepository.delete(employee);
    }
}
