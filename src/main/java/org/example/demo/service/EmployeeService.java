package org.example.demo.service;

import org.example.demo.entity.Employee;
import org.example.demo.exception.EmployeeNotExsitingException;
import org.example.demo.exception.PageNotFoundException;
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
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> getEmployees() {
        return employees;
    }
    public Employee setEmployees(Employee employee) {
        if (employees.isEmpty()){
            employee.setId(1);
        }else {
            employee.setId(employees.get(employees.size() - 1).getId()+1);
        }
        employees.add(employee);
        return employee;
    }
    public Employee getEmployeeById(long id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);
    }
    public List<Employee> getEmployeeByGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).toList();

    }
    public void deleteEmployee(long id) {
        Employee employee = employees.stream().filter(employee2 -> employee2.getId() == id).findFirst().orElse(null);
        employees.remove(employee);
    }
    public List<Employee> getEmployeeByPage(int page, int size) {
        if (employees.size() < (page - 1) * size) {
            throw new PageNotFoundException();
        }
        return employees.stream()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }
    public Employee updateEmployee(Employee employee, long id) {
        Employee employee1 = employees.stream().filter(employee2 -> employee2.getId() == id).findFirst().orElse(null);
        if (employee1 == null) throw new EmployeeNotExsitingException();
        employee.setId(employee1.getId());
        return employee;
    }

    public void clearEmployees() {
        employees.clear();
    }

}
