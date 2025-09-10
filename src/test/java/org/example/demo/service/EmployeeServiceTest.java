package org.example.demo.service;

import org.example.demo.entity.Employee;
import org.example.demo.exception.AgeOutOfLegalRangeException;
import org.example.demo.exception.IdNotExsitingException;
import org.example.demo.resposity.EmployeeRepository;
import org.example.demo.resposity.impl.EmployeeRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepositoryImpl employeeRepositoryImpl;
    @InjectMocks
    private EmployeeService employeeService;


    @Test
    public void should_throw_exception_when_put_employee_given_age_within_range_lt() {
        Employee employee = new Employee("Tom", 17, 10000, "male");
        verify(employeeRepositoryImpl, times(0)).save(employee);
        assertThrows(AgeOutOfLegalRangeException.class, () -> {
            employeeService.setEmployees(employee);
        });
    }
    @Test
    public void should_throw_exception_when_put_employee_given_age_within_range_gt() {
        Employee employee = new Employee("Tom", 70, 10000, "male");
        verify(employeeRepositoryImpl, times(0)).save(employee);
        assertThrows(AgeOutOfLegalRangeException.class, () -> {
            employeeService.setEmployees(employee);
        });
    }
    @Test
    public void should_return_employee_when_get_employee_given_id() {
        Employee employee = new Employee("Tom", 70, 10000, "male");
        doReturn(Optional.of(employee)).when(employeeRepositoryImpl).findById(1L);
        employeeService.getEmployeeById(1);
        verify(employeeRepositoryImpl, times(1)).findById(1L);
    }
    @Test
    public void should_throw_exception_when_get_employee_given_employee_id_invalid() {
        doThrow(new IdNotExsitingException()).when(employeeRepositoryImpl).findById(1L);
        assertThrows(IdNotExsitingException.class, ()->{
            employeeService.getEmployeeById(1L);
        });

    }
}
