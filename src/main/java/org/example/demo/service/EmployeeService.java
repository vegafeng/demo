package org.example.demo.service;

import org.example.demo.entity.Employee;
import org.example.demo.exception.*;
import org.example.demo.resposity.impl.EmployeeRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (employee.getAge()>=30 && employee.getSalary()<20000){
            throw new InvalidSalaryException();
        }
        if (employeeRepositoryImpl.findAll().stream()
                .anyMatch(employee1 -> employee1.equals(employee))) {
            throw new EmployeeAlreadyExistsException();
        }
        employeeRepositoryImpl.save(employee);
        return employee;
    }
    public Optional<Employee> getEmployeeById(long id) {
        if (employeeRepositoryImpl.findById(id)==null) {
            throw new IdNotExsitingException();
        }
        return employeeRepositoryImpl.findById(id);
//        if (employeeRepositoryImpl.findById(id).isPresent()) {
//            return employeeRepositoryImpl.findById(id);
//        }
//        throw new IdNotExsitingException();

    }
    public List<Employee> getEmployeeByGender(String gender) {

        return employeeRepositoryImpl.findAll().stream().filter(employee -> employee.getGender().equals(gender)).toList();

    }
    public void deleteEmployee(long id) {
        employeeRepositoryImpl.delete(employeeRepositoryImpl.findById(id).get());
    }
    public List<Employee> getEmployeeByPage(int page, int size) {
        List<Employee> employees = employeeRepositoryImpl.findAll();
        if (employees.size()<(page-1)*size) {
            throw new PageNotFoundException();
        }
        return employees.stream()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }
    public Employee updateEmployee(Employee employee, long id) {

//        return employeeRepository.update(employee, id);
        if (!employeeRepositoryImpl.existsById(id)) throw new EmployeeNotExsitingException();
        Employee employee1 = employeeRepositoryImpl.findById(id).get();
        employee1.setName(employee.getName());
        employee1.setAge(employee.getAge());
        employee1.setGender(employee.getGender());
        return employeeRepositoryImpl.save(employee1);

    }

    public void clearEmployees() {
        employeeRepositoryImpl.findAll().clear();
    }

}
