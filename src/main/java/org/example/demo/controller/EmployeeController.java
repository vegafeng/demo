package org.example.demo.controller;

import org.example.demo.entity.Employee;
import org.example.demo.exception.EmployeeNotExsitingException;
import org.example.demo.exception.PageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>();

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
    @GetMapping(value = "/employees",params = {"gender"})
    public List<Employee> getEmployeeByGender(@RequestParam String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).toList();
    }
    @DeleteMapping("/employees/{id}")
    public ResponseEntity deleteEmployee(@PathVariable long id) {
        Employee employee = employees.stream().filter(employee2 -> employee2.getId() == id).findFirst().orElse(null);
        employees.remove(employee);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/employees", params = {"page", "size"})
    public List<Employee> getEmployeesByPage(@RequestParam int page, @RequestParam int size) {
        List<Employee> employeeList=new ArrayList<>();
        if (employees.size()<(page-1)*size) throw new PageNotFoundException();

        for (int i=(page-1)*size;i<page*size;i++) {
                if (i<employees.size()) {
                    employeeList.add(employees.get(i));
                }

        }

        return employeeList;
    }
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable long id) throws Exception {
        Employee employee1 = employees.stream().filter(employee2 -> employee2.getId() == id).findFirst().orElse(null);
        if (employee1 == null) throw new EmployeeNotExsitingException();
        employee.setId(employee1.getId());
        return new ResponseEntity(employee, HttpStatus.NO_CONTENT);

    }
    public void clearEmployees() {
        employees.clear();
    }
}
