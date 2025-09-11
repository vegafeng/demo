package org.example.demo.controller;

import org.example.demo.dto.UpdateEmployeeReq;
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
        employeeService.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable long id) {
        return employeeService.getEmployeeById(id);
    }
    @GetMapping(value = "/employees",params = {"gender"})
    public List<Employee> getEmployeeByGender(@RequestParam String gender) {
        return employeeService.getEmployeeByGender(gender);
    }
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/employees", params = {"page", "size"})
    public List<Employee> getEmployeesByPage(@RequestParam int page, @RequestParam int size) {
        return employeeService.getEmployeeByPage(page, size);
    }
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody UpdateEmployeeReq updateEmployeeReq, @PathVariable long id) throws Exception {
        return new ResponseEntity<>(employeeService.updateEmployee(updateEmployeeReq, id), HttpStatus.NO_CONTENT);

    }
//
//    @DeleteMapping(value = "/employees", params = {"initId", "finalId"})
//    public ResponseEntity<Void> deleteEmployeesInRange(@RequestParam Long initId, @RequestParam Long finalId) {
//        employeeService.deleteEmployeesInRange(initId, finalId);
//        return ResponseEntity.noContent().build();
//    }
    public void clearEmployees(long id) {
        employeeService.clearEmployees();
        employeeService.deleteEmployee(id);
    }
}
