package org.example.demo.controller;

import org.example.demo.entity.Employee;
import org.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author FENGVE
 */
@RestController
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/employees")
    public List<Employee> getEmployee() {
        return employeeService.getEmployees();
    }
    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employeeService.setEmployees(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
    @GetMapping("/employees/{id}")
    public Optional<Employee> getEmployeeById(@PathVariable long id) {
        return employeeService.getEmployeeById(id);
    }
    @GetMapping(value = "/employees",params = {"gender"})
    public List<Employee> getEmployeeByGender(@RequestParam String gender) {
        return employeeService.getEmployeeByGender(gender);
    }
    @DeleteMapping("/employees/{id}")
    public ResponseEntity deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
//    @GetMapping(value = "/employees", params = {"page", "size"})
//    public List<Employee> getEmployeesByPage(@RequestParam int page, @RequestParam int size) {
//        return employeeService.getEmployeeByPage(page, size);
//    }
//    @PutMapping("/employees/{id}")
//    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable long id) throws Exception {
//        return new ResponseEntity<>(employeeService.updateEmployee(employee, id), HttpStatus.NO_CONTENT);
//
//    }
    public void clearEmployees() {
        employeeService.clearEmployees();
    }
}
