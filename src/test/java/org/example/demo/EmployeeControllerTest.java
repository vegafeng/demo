package org.example.demo;

import org.example.demo.controller.EmployeeController;
import org.example.demo.entity.Employee;
import org.example.demo.exception.ExceptionMsg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeController employeeController;

    Employee employee = new Employee("Tom", 18, 10000, "male");
    Employee employee2 = new Employee("Tom", 18, 10000, "female");
    Employee employee3 = new Employee("Mickey", 35, 60000, "male");
    Employee employee4 = new Employee("Donald", 28, 45000, "male");
    @BeforeEach
    public void setUp() {
        employeeController.clearEmployees();
    }

    @Test
    public void should_return_id_when_post_given_a_valid_employee() throws Exception {
        String requestBody = """
                {
                    "name": "Tom",
                    "salary": 1000,
                    "gender": "male"
                }
                """;
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.id").value(1));
    }
    @Test
    public void should_return_employees_when_get_all_given_null() throws Exception {
        employeeController.addEmployee(employee);
        mockMvc.perform(get("/employees").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()));
    }
    @Test
    public void should_return_employees_when_get_given_id() throws Exception {
        employeeController.addEmployee(employee);
        mockMvc.perform(get("/employees/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(employee.getSalary()));
    }

    @Test
    public void should_return_employees_when_get_given_gender() throws Exception {
        employeeController.addEmployee(employee);
        employeeController.addEmployee(employee2);
        mockMvc.perform(get("/employees?gender=male")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(employee.getId()))
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$.length()").value(1));
    }
    @Test
    public void should_response_no_content_when_delete_given_employee_id() throws Exception {
        employeeController.addEmployee(employee);
        mockMvc.perform(delete("/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    public void should_return_employees_when_get_by_page_given_page_size() throws Exception {
        employeeController.addEmployee(employee);
        employeeController.addEmployee(employee2);
        employeeController.addEmployee(employee3);
        employeeController.addEmployee(employee4);
        mockMvc.perform(get("/employees?page=1&size=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$[0].salary").value(employee.getSalary()))
                .andExpect(jsonPath("$.length()").value(4));
    }
    @Test
    public void should_return_matching_code_when_update_by_id_given_age_salary() throws Exception {
        employeeController.addEmployee(employee);
        String requestBody = """
                {
                    "name": "Tom",
                    "salary": 1000,
                    "age": 20,
                    "gender": "male"
                }
                """;
        mockMvc.perform(put("/employees/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value(employee.getName()))
                .andExpect(jsonPath("$.gender").value(employee.getGender()))
                .andExpect(jsonPath("$.salary").value(1000))
                .andExpect(jsonPath("$.age").value(20));
    }


    @Test
    void should_throw_exception_when_get_given_page_out_of_all() throws Exception {
        employeeController.addEmployee(employee);
        employeeController.addEmployee(employee2);
        employeeController.addEmployee(employee3);
        employeeController.addEmployee(employee4);
        mockMvc.perform(get("/employees?page=2&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(ExceptionMsg.Page_Not_Found)));

    }


}
