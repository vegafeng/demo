package org.example.demo.resposity.impl;

import org.example.demo.entity.Employee;
import org.example.demo.resposity.EmployeeRepository;
import org.example.demo.resposity.jpa.EmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @author FENGVE
 */
@Repository
public class EmployeeRepositoryDBImpl implements EmployeeRepository {
    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;
    @Override
    public Employee findEmployeeById(long id) {
        return employeeJpaRepository.findById(id).get();
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeJpaRepository.findAll();
    }

    @Override
    public Employee saveEmployee(Employee employee) {
//        return employeeJpaRepository.save(employee);
        return employeeJpaRepository.save(employee);

    }


    @Override
    public void deleteEmployeeById(Employee employee, long id) {
        employeeJpaRepository.deleteById(id);
    }


    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeJpaRepository.save(employee);
    }

    @Override
    public List<Employee> findEmployeesByPage(int page, int size) {
//        return List.of();
        Page<Employee> employeePage = employeeJpaRepository.findAll(PageRequest.of(page, size));
        return employeePage.getContent();
    }

    @Override
    public List<Employee> findEmployeesByGender(String gender) {
        return employeeJpaRepository.findAll().stream().filter(employee -> employee.getGender().equals(gender)).toList();
    }


}
