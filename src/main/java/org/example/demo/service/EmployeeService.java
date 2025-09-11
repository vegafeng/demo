package org.example.demo.service;

import org.example.demo.dto.UpdateEmployeeReq;
import org.example.demo.entity.Employee;
import org.example.demo.exception.*;
import org.example.demo.resposity.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FENGVE
 */
@Service
public class EmployeeService {
//    @Autowired
//    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getEmployees() {
        return employeeRepository.findAllEmployees();
    }
    public Employee save(Employee employee) {
        if (employee.getAge()<18 || employee.getAge()>65) {
            throw new AgeOutOfLegalRangeException();
        }
        if (employee.getAge()>=30 && employee.getSalary()<20000){
            throw new InvalidSalaryException();
        }
        if (employeeRepository.findAllEmployees().stream()
                .anyMatch(employee1 -> employee1.equals(employee))) {
            throw new EmployeeAlreadyExistsException();
        }
        employeeRepository.saveEmployee(employee);
        return employee;
    }
    public Employee getEmployeeById(long id) {
        if (!employeeRepository.findEmployeeById(id).getStatus()) {
            throw new IdNotExsitingException();
        }
        return employeeRepository.findEmployeeById(id);

    }
    public List<Employee> getEmployeeByGender(String gender) {

        return employeeRepository.findAllEmployees().stream().filter(employee -> employee.getGender().equals(gender)).toList();

    }
    public void deleteEmployee(long id) {
        if (!employeeRepository.findEmployeeById(id).getStatus()){
            throw new EmployeeNotExsitingException();
        }
        employeeRepository.findEmployeeById(id).setStatus(false);
    }

    public List<Employee> getEmployeeByPage(int page, int size) {
        List<Employee> employees = employeeRepository.findAllEmployees();
        if (employees.size()<(page-1)*size) {
            throw new PageNotFoundException();
        }
        return employees.stream()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }
    public Employee updateEmployee(UpdateEmployeeReq employee, long id) {

        if (!employeeRepository.findEmployeeById(id).getStatus()) throw new EmployeeNotExsitingException();
        Employee employeeExisting = employeeRepository.findEmployeeById(id);
        employeeExisting.setName(employee.getName());
        employeeExisting.setGender(employee.getGender());
        employeeExisting.setAge(employee.getAge());
//        employee.setName(employee.getName());
//        employee.setAge(employee.getAge());
//        employee.setGender(employee.getGender());
        employeeRepository.saveEmployee(employeeExisting);
        return employeeExisting;

    }

//
//    public void deleteEmployeesInRange(Long initId, Long finalId) {
//        List<Employee> employeesToDelete = employeeRepository.findAllByIdBetween(initId, finalId);
//        employeeRepository.deleteAll(employeesToDelete);
//    }

    public void clearEmployees() {
        employeeRepository.findAllEmployees().clear();
    }

}
