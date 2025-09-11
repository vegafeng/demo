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

    private String requestBody;
    private String requestBody2;




    Employee employee = new Employee("Tombb", 18, 10000, "male");
    Employee employee2 = new Employee("Tomuu", 18, 10000, "female");
    Employee employee3 = new Employee("Mickeyqq", 35, 60000, "male");
    Employee employee4 = new Employee("Donaldss", 28, 45000, "male");
    private List<Employee> employees = new ArrayList<>();
    private long INIT_LENGTH = 0;
    @BeforeEach
    public void setUp() {
        employees = employeeController.getEmployee();
        INIT_LENGTH = employees.size();
        Company company = new Company("cosco");
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
                    "age": 31
                }
                """;
        mockMvc.perform(post("/employees").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isBadRequest())
                .andExpect(content().string(containsString(ExceptionMsg.INVALID_SALARY_EXCEPTION)));
    }


    @Test
    public void should_return_employees_when_get_all_given_null() throws Exception {

        mockMvc.perform(get("/employees").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(INIT_LENGTH));
    }
    @Test
    public void should_return_employee_when_get_given_id() throws Exception {
        employeeController.addEmployee(employee);
        mockMvc.perform(get("/employees/{id}", 2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(1000));
    }

    @Test
    public void should_return_employees_when_get_given_gender() throws Exception {
        employeeController.addEmployee(employee);
        employeeController.addEmployee(employee2);
        mockMvc.perform(get("/employees?gender=male")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(employee.getName()))
                .andExpect(jsonPath("$[0].gender").value(employee.getGender()))
                .andExpect(jsonPath("$.length()").value(9));
    }
//    @Test
//    @Transactional
//    public void should_response_no_content_when_delete_given_employee_id() throws Exception {
////        ResponseEntity<Employee> employeeResponseEntity = employeeController.addEmployee(employee);
//        String requestBody = """
//                {
//                    "name": "5",
//                    "salary": 1000,
//                    "gender": "male",
//                    "age": 20
//                }
//                """;
//
//        MvcResult result = mockMvc.perform(post("/employees")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.name").value("5"))
//                .andReturn(); // 获取 MvcResult
//
//        // 从 MvcResult 中提取响应体
//        String jsonResponse = result.getResponse().getContentAsString();
//        ObjectMapper objectMapper = new ObjectMapper();
//        Employee createdEmployee = objectMapper.readValue(jsonResponse, Employee.class);
////        mockMvc.perform(post("/employees").
////                        contentType(MediaType.APPLICATION_JSON).
////                        content(requestBody)).
////                andExpect(status().isCreated()).
////                andExpect(jsonPath("$.name").value("5"))
////        ;
//        long id = createdEmployee.getId();
//        System.out.println(id);
//        List<Employee> employees1 = employeeController.getEmployee();
//        for (Employee employee : employees1) {
//            System.out.println(String.valueOf(employee.getId())+ employee.getStatus());
//        }
//        mockMvc.perform(delete("/employees/{id}", id)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNoContent());
//    }
    @Test
    public void should_response_no_content_when_delete_given_employee_id() throws Exception {
        ResponseEntity<Employee> employeeResponseEntity = employeeController.addEmployee(employee);
        long id = employeeResponseEntity.getBody().getId();
        mockMvc.perform(delete("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/employees/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_return_employees_when_get_by_page_given_page_size() throws Exception {
        employeeController.addEmployee(employee);
        employeeController.addEmployee(employee2);
        employeeController.addEmployee(employee3);
        employeeController.addEmployee(employee4);
        long id = createEmployee(EmployeeUtils.convertEmployeeToJson(employee));
        mockMvc.perform(get("/employees?page=1&size=5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + id + ".name").value(employee.getName()))
                .andExpect(jsonPath("$." + id + ".name").value(employee.getGender()))
                .andExpect(jsonPath("$." + id + ".name").value(employee.getSalary()))
                .andExpect(jsonPath("$.length()").value(4));
    }
    @Autowired
    private CompanyRepository companyRepository;
@Autowired
private EmployeeRepository employeeRepository;
    @Test
    public void should_return_matching_code_when_update_by_id_given_age_salary() throws Exception {

        Company company = new Company("cosco");
        companyRepository.save(company);
//        Employee employee = new Employee("Tombb", 18, 10000, "male");
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
        ResponseEntity<Employee> employeeResponseEntity = employeeController.addEmployee(employee);
        long id = employeeResponseEntity.getBody().getId();
        String requestBody = """
                {
                    "name": "kaka",
                    "salary": 1000,
                    "age": 20,
                    "gender": "male",
                    "status": false
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
                    "name": "Tom",
                    "salary": 1000,
                    "age": 20,
                    "gender": "male",
                    "status": false
                }
                """;

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
