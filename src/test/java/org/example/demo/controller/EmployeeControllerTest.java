package org.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.demo.entity.Company;
import org.example.demo.entity.Employee;
import org.example.demo.exception.ExceptionMsg;
import org.example.demo.resposity.CompanyRepository;
import org.example.demo.resposity.EmployeeRepository;
import org.example.demo.util.employee.EmployeeUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeController employeeController;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    private String requestBody;
    private String requestBody2;
    private Company company;
    Employee employee = new Employee("Tombb", 18, 10000, "male");
    private List<Employee> employees = new ArrayList<>();
    private long INIT_LENGTH = 0;
    @BeforeEach
    public void setUp() {
        employees = employeeController.getEmployee();
        INIT_LENGTH = employees.size();
        company = new Company("cosco");
        companyRepository.save(company);
        requestBody = """
                {
                    "name": "lala",
                    "salary": 1000,
                    "gender": "male",
                    "age": 20,
                    "status": true,
                    "companyId": %d
                }
                """.formatted(company.getId());
        requestBody2 = """
                {
                    "name": "kaka",
                    "salary": 200000,
                    "gender": "female",
                    "age": 30,
                    "status": true,
                    "companyId": %d
                }
                """.formatted(company.getId());

    }

    @Test
    public void should_return_id_when_post_given_a_valid_employee() throws Exception {
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name").value("lala"))
        ;
        assertEquals(employeeController.getEmployee().size(), INIT_LENGTH+1);
    }
    @Test
    public void should_return_id_when_post_given_different_employee() throws Exception {
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name").value("lala"));
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody2)).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name").value("kaka"));
        assertEquals(employeeController.getEmployee().size(), INIT_LENGTH+2);
    }

    @Test
    public void should_throw_exception_when_post_given_same_employee() throws Exception {
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name").value("lala"));
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isBadRequest());
    }


    @Test
    public void should_throw_exception_when_post_given_an_invalid_employee_lt() throws Exception {
        String requestBody = """
                {
                    "name": "Tomll",
                    "salary": 1000,
                    "gender": "male",
                    "age": 17,
                    "status": true,
                    "companyId": %d
                }
                """.formatted(employee.getId());
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ExceptionMsg.AGE_OUT_OF_LEGAL_RANGE)));
    }
    @Test
    public void should_throw_exception_when_post_given_an_invalid_employee_gt() throws Exception {
        String requestBody = """
                {
                    "name": "Tompp",
                    "salary": 1000,
                    "gender": "male",
                    "age": 70,
                    "status": true,
                    "companyId": %d
                }
                """.formatted(employee.getId());
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ExceptionMsg.AGE_OUT_OF_LEGAL_RANGE)));
    }
    @Test
    public void should_throw_exception_when_post_given_an_invalid_age_salary() throws Exception {
        String requestBody = """
                {
                    "name": "Tomyy",
                    "salary": 1000,
                    "gender": "male",
                    "age": 31,
                    "status": true,
                    "companyId": %d
                }
                """.formatted(employee.getId());
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ExceptionMsg.INVALID_SALARY_EXCEPTION)));
    }


    @Test
    public void should_return_employees_when_get_all_given_null() throws Exception {
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.name").value("lala"))
        ;
        mockMvc.perform(get("/employees").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(INIT_LENGTH+1));
    }
    @Test
    public void should_return_employee_when_get_given_id() throws Exception {
        long id = createEmployee(requestBody);
        mockMvc.perform(get("/employees/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("lala"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    public void should_return_employees_when_get_given_gender() throws Exception {
        createEmployee(requestBody);
        createEmployee(requestBody2);
        mockMvc.perform(get("/employees?gender=male")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$["+INIT_LENGTH+"].name").value("lala"))
                .andExpect(jsonPath("$["+INIT_LENGTH+"].gender").value("male"));
    }

    @Test
    public void should_response_no_content_when_delete_given_employee_id() throws Exception {
        long id = createEmployee(requestBody);
        mockMvc.perform(delete("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_employees_when_get_by_page_given_page_size() throws Exception {
        createEmployee(requestBody);
        createEmployee(requestBody2);
        long index = INIT_LENGTH + 1;
        mockMvc.perform(get("/employees?page=1&size=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[" + index + "].name").value("kaka"))
                .andExpect(jsonPath("$.[" + index + "].gender").value("female"))
                .andExpect(jsonPath("$.[" + index + "].salary").value(200000));
    }

    @Test
    public void should_return_matching_code_when_update_by_id_given_age_salary() throws Exception {
        Employee employee = new Employee();
        employee.setName("Tombb");
        employee.setGender("male");
        employee.setSalary(1000);
        employee.setAge(18);
        employee.setStatus(true);
        employee.setCompanyId(company.getId());
        employeeRepository.saveEmployee(employee);
        long id = employee.getId();


        String requestBody = """
                {
                    "name": "Tom",
                    "age": 20,
                    "gender": "male"
                }
                """;
        mockMvc.perform(put("/employees/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.age").value(20));
    }
    @Test
    public void should_return_matching_code_when_update_given_status_false() throws Exception {

        Employee employee = new Employee();
        employee.setName("Tombb");
        employee.setGender("male");
        employee.setSalary(1000);
        employee.setAge(18);
        employee.setStatus(false);
        employee.setCompanyId(company.getId());
        employeeRepository.saveEmployee(employee);
        long id = employee.getId();

        String requestBody = """
                {
                    "name": "kaka",
                    "age": 20,
                    "gender": "male"
                }
                """;
        mockMvc.perform(put("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }


    @Test
    void should_throw_exception_when_get_given_page_out_of_all() throws Exception {
        mockMvc.perform(get("/employees?page=2&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(ExceptionMsg.PAGE_NOT_FOUND)));

    }
    @Test
    void should_throw_exception_when_get_given_employee_not_existing() throws Exception {

        String requestBody = """
                {
                    "name": "lala",
                    "salary": 1000,
                    "gender": "male",
                    "age": 20,
                    "status": false,
                    "companyId": %d
                }
                """.formatted(company.getId());

        long id = createEmployee(requestBody);
        mockMvc.perform(get("/employees/{id}", id))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ExceptionMsg.ID_NOT_EXSITING_EXCEPTION)));

    }

    private long createEmployee(String requestBody) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readTree(contentAsString).get("id").asLong();
    }
}
