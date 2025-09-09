package org.example.demo.controller;

import org.example.demo.entity.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {
    private List<Employee> employees = new ArrayList<Employee>();

    @GetMapping("/employees")
    public List<Employee> getEmployee() {
        return employees;
    }
    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employee.setId(employees.size()+1);
        employees.add(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable long id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);
    }
    @GetMapping("/employees1")
    public List<Employee> getEmployeeByGender(@RequestParam String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).toList();
    }

}
