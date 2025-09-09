package org.example.demo;

import org.example.demo.controller.EmployeeController;
import org.example.demo.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeController employeeController;

    Employee employee = new Employee("Tom", 18, 10000, "male");
    Employee employee2 = new Employee("Tom", 18, 10000, "female");

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
        mockMvc.perform(get("/employees1?gender=male")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
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


}
