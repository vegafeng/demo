package org.example.demo.service;

import org.example.demo.entity.Employee;
import org.example.demo.exception.EmployeeNotExsitingException;
import org.example.demo.exception.PageNotFoundException;
import org.example.demo.resposity.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FENGVE
 */
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
    public Employee setEmployees(Employee employee) {
        employeeRepository.save(employee);
        return employee;
    }
    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }
    public List<Employee> getEmployeeByGender(String gender) {
        return employeeRepository.findByGender(gender);

    }
    public void deleteEmployee(long id) {
        employeeRepository.delete(id);
    }
    public List<Employee> getEmployeeByPage(int page, int size) {
        return employeeRepository.findByPage(page, size);
    }
    public Employee updateEmployee(Employee employee, long id) {
        if (employeeRepository.update(employee, id)==null){
            throw new EmployeeNotExsitingException();
        }
        return employeeRepository.update(employee, id);
    }

    public void clearEmployees() {
        List<Employee> employees = employeeRepository.findAll();
            employees.clear();
    }

}
