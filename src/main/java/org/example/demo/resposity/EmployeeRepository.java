package org.example.demo.resposity;

import org.example.demo.entity.Company;
import org.example.demo.entity.Employee;
import org.example.demo.exception.PageNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Repository
public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findById(long id) {
        return employees.stream().filter(employee -> employee.getId() == id).findFirst().orElse(null);

    }
    public Employee save(Employee employee) {
        if (employees.isEmpty()) {
            employee.setId(1);
        } else {
            employee.setId(employees.get(employees.size() - 1).getId() + 1);
        }
        employees.add(employee);
        return employee;
    }
    public Employee update(Employee employee) {
        int index = employees.indexOf(employee);
        if (index != -1) {
            employees.set(index, employee);
        }
        return employee;
    }
    public Employee delete(long id) {
        int index = employees.indexOf(findById(id));
        if (index != -1) {
            employees.remove(index);
        }
        return findById(id);
    }
    public List<Employee> findByPage(int page, int size) {
        if (employees.size() < (page - 1) * size) {
            throw new PageNotFoundException();
        }
        return employees.stream()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }
    public List<Employee> findByGender(String gender) {
        return  employees.stream().filter(employee -> employee.getGender().equals(gender)).toList();
    }

}
