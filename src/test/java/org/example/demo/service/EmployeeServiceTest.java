package org.example.demo.service;

import org.assertj.core.util.Lists;
import org.example.demo.entity.Employee;
import org.example.demo.exception.*;
import org.example.demo.resposity.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeService employeeService;


    @Test
    public void should_throw_exception_when_put_employee_given_age_within_range_lt() {
        Employee employee = new Employee("Tom", 17, 10000, "male");
        verify(employeeRepository, times(0)).saveEmployee(employee);
        assertThrows(AgeOutOfLegalRangeException.class, () -> {
            employeeService.save(employee);
        });
    }
    @Test
    public void should_throw_exception_when_put_employee_given_age_within_range_gt() {
        Employee employee = new Employee("Tom", 70, 10000, "male");
        verify(employeeRepository, times(0)).saveEmployee(employee);
        assertThrows(AgeOutOfLegalRangeException.class, () -> {
            employeeService.save(employee);
        });
    }
    @Test
    public void should_return_employee_when_get_employee_given_id() {
        Employee employee = new Employee("Tom", 70, 10000, "male");
        doReturn(employee).when(employeeRepository).findEmployeeById(1L);
        employeeService.getEmployeeById(1);
        verify(employeeRepository, times(2)).findEmployeeById(1L);
    }
    @Test
    public void should_throw_exception_when_get_employee_given_employee_id_invalid() {
        doThrow(new IdNotExsitingException()).when(employeeRepository).findEmployeeById(1L);
        assertThrows(IdNotExsitingException.class, ()->{
            employeeService.getEmployeeById(1L);
        });
        verify(employeeRepository, times(1)).findEmployeeById(1L);

    }
    @Test
    public void should_throw_exception_when_get_employee_given_salary_invalid(){
        Employee employee = new Employee("Tom", 31, 10000, "male");
        doReturn(null).when(employeeRepository).saveEmployee(employee);
        assertThrows(InvalidSalaryException.class, ()->{
            employeeService.save(employee);
        });
        verify(employeeRepository, never()).saveEmployee(employee);
    }
    @Test
    public void should_return_employee_when_put_employee_given_age_salary_within_range() {
        Employee employee = new Employee("Tom", 31, 30000, "male");
        employeeService.save(employee);
        verify(employeeRepository, times(1)).saveEmployee(employee);
    }
    @Test
    public void should_return_employee_when_put_employee_given_age_salary_within_range_age_lt_standard() {
        Employee employee = new Employee("Tom", 20, 30000, "male");
        employeeService.save(employee);
        verify(employeeRepository, times(1)).saveEmployee(employee);
    }
    @Test
    public void should_return_employee_when_put_employee_given_different_name() {
        Employee employee = new Employee("Tom", 20, 30000, "male");
        Employee employee2 = new Employee("Jim", 20, 30000, "male");

        employeeService.save(employee);
        employeeService.save(employee2);
        verify(employeeRepository, times(2)).saveEmployee(any(Employee.class));
    }
    @Test
    public void should_throw_exception_when_put_employee_given_same_name_gender() {
        Employee employee = new Employee("Tom", 20, 30000, "male");
        Employee employee2 = new Employee("Tom", 20, 30000, "male");

        employeeService.save(employee);

        doReturn(Lists.newArrayList(employee)).when(employeeRepository).findAllEmployees();

        verify(employeeRepository, times(1)).saveEmployee(any(Employee.class));
        assertThrows(EmployeeAlreadyExistsException.class, ()->{
            employeeService.save(employee2);
        });
    }
    @Test
    public void should_throw_exception_when_delete_employee_given_status_false(){
        Employee employee = new Employee(123, "Tom", 20, 30000, "male", false);
        doReturn(employee).when(employeeRepository).findEmployeeById(123L);
        assertThrows(EmployeeNotExsitingException.class, ()->{
            employeeService.deleteEmployee(123);
        });

    }

    @Test
    public void should_return_employee_when_delete_employee_given_status_true(){
        Employee employee = new Employee(123, "Tom", 20, 30000, "male", true);
        doReturn(employee).when(employeeRepository).findEmployeeById(123L);
        employeeService.deleteEmployee(123);
//        verify(employeeRepository, times(1)).delete(employee);
        verify(employeeRepository, times(2)).findEmployeeById(anyLong());

    }
    @Test
    public void should_return_employee_when_update_employee_given_status_true(){
        Employee employee = new Employee(123, "Tom", 20, 30000, "male", true);
        Employee employee2 = new Employee("Tom", 20, 30000, "male");
        doReturn(employee).when(employeeRepository).findEmployeeById(123L);
//        employeeService.updateEmployee(employee2, 123L);
        verify(employeeRepository, times(2)).findEmployeeById(123L);
    }
    @Test
    public void should_throw_exception_when_update_employee_given_status_true(){
        Employee employee = new Employee(123, "Tom", 20, 30000, "male", false);
        Employee employee2 = new Employee("Tom", 20, 30000, "male");
        doReturn(employee).when(employeeRepository).findEmployeeById(123L);
        assertThrows(EmployeeNotExsitingException.class, ()->{
//            employeeService.updateEmployee(employee2, 123L);
        });
    }

}
