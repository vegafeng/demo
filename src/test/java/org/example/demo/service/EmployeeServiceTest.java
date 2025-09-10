package org.example.demo.service;

import org.example.demo.entity.Employee;
import org.example.demo.exception.AgeOutOfLegalRangeException;
import org.example.demo.resposity.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;


    @Test
    public void should_throw_exception_when_put_employee_given_age_within_range_lt() {
        Employee employee = new Employee("Tom", 17, 10000, "male");
        verify(employeeRepository, times(0)).save(employee);
        assertThrows(AgeOutOfLegalRangeException.class, () -> {
            employeeService.setEmployees(employee);
        });
    }
    @Test
    public void should_throw_exception_when_put_employee_given_age_within_range_gt() {
        Employee employee = new Employee("Tom", 70, 10000, "male");
        verify(employeeRepository, times(0)).save(employee);
        assertThrows(AgeOutOfLegalRangeException.class, () -> {
            employeeService.setEmployees(employee);
        });
    }
}
