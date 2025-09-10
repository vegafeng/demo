package org.example.demo.service;

import org.example.demo.entity.Employee;
import org.example.demo.exception.AgeOutOfLegalRangeException;
import org.example.demo.resposity.impl.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author FENGVE
 */
@Service
public class EmployeeService {
//    @Autowired
//    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeRepositoryImpl employeeRepositoryImpl;

    public List<Employee> getEmployees() {
        return employeeRepositoryImpl.findAll();
    }
    public Employee setEmployees(Employee employee) {
        if (employee.getAge()<18 || employee.getAge()>65) {
            throw new AgeOutOfLegalRangeException();
        }
        employeeRepositoryImpl.save(employee);
        return employee;
    }
    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepositoryImpl.findById(id);
    }
    public List<Employee> getEmployeeByGender(String gender) {

        return employeeRepositoryImpl.findAll().stream().filter(employee -> employee.getGender().equals(gender)).toList();

    }
    public void deleteEmployee(long id) {
        employeeRepositoryImpl.delete(employeeRepositoryImpl.findById(id).get());
    }
//    public List<Employee> getEmployeeByPage(int page, int size) {
//        return employeeRepository.findByPage(page, size);
//    }
//    public Employee updateEmployee(Employee employee, long id) {
//        if (employeeRepository.update(employee, id)==null){
//            throw new EmployeeNotExsitingException();
//        }
//        return employeeRepository.update(employee, id);
//    }

    public void clearEmployees() {
        List<Employee> employees = employeeRepositoryImpl.findAll();
            employees.clear();
    }

}
