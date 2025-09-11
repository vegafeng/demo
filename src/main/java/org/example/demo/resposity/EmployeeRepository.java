package org.example.demo.resposity;

import org.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository {

    public Employee findEmployeeById(long id);

    public List<Employee> findAllEmployees();
    public Employee saveEmployee(Employee employee);
    public void deleteEmployeeById(Employee employee, long id);
    public Employee updateEmployee(Employee employee);
    public List<Employee> findEmployeesByPage(int page, int size);
    public List<Employee> findEmployeesByGender(String gender);
    public void deleteAll();

}
