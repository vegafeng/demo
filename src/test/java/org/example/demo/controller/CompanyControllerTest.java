package org.example.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.demo.entity.Company;
import org.example.demo.exception.ExceptionMsg;
import org.example.demo.resposity.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyController companyController;
    @Autowired
    private EmployeeRepository employeeRepository;
    private Company company = new Company("cosco");
    private Company company2 = new Company("oocl");
    private Company company3 = new Company("sony");
    private Company company4 = new Company("sam");
    private long INIT_LENGTH = 0;
    private List<Company> companies = new ArrayList<>();
    private String requestBody;
    private String requestBody2;
    @BeforeEach
    public void setUp() {


        companies = companyController.getCompanies();
        INIT_LENGTH = companies.size();
        requestBody = """
                {
                    "name": "oocl"
                }
                """;
        requestBody2 = """
                {
                    "name": "cosco"
                }
                """;
    }

    @Test
    public void should_return_company_when_post_given_a_valid_company() throws Exception {
        String requestBody = """
                {
                    "name": "cosco"
                }
                """;
        mockMvc.perform(post("/companies").
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("cosco"));
        assertEquals(companyController.getCompanies().size(), INIT_LENGTH+1);
    }
    @Test
    public void should_return_companies_when_get_all_given_null() throws Exception{
        createCompany(requestBody);
        createCompany(requestBody2);
        mockMvc.perform(get("/companies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(INIT_LENGTH+2));
    }

    @Test
    public void should_return_employees_when_get_given_id() throws Exception {
        long id = createCompany(requestBody);
        mockMvc.perform(get("/companies/{id}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("oocl"));
    }
    @Test
    public void should_return_companies_when_get_by_page_given_page_size() throws Exception {
        createCompany(requestBody);
        createCompany(requestBody2);
        mockMvc.perform(get("/companies?page=1&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5));
    }
    @Test
    public void should_return_matching_code_when_update_by_id_given_age_salary() throws Exception {
        long id = createCompany(requestBody);
        mockMvc.perform(put("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody2))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.name").value("cosco"));
    }
    @Test
    public void should_response_no_content_when_delete_given_employee_id() throws Exception {
        long id = createCompany(requestBody);
        mockMvc.perform(delete("/companies/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_throw_exception_when_get_given_page_out_of_all() throws Exception {
        companyController.addCompany(company);
        companyController.addCompany(company2);
        companyController.addCompany(company3);
        companyController.addCompany(company4);
        mockMvc.perform(get("/companies?page=2&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(ExceptionMsg.PAGE_NOT_FOUND)));
    }
    @Test
    public void should_throw_exception_when_get_given_company_not_exsiting() throws Exception {
        companyController.addCompany(company);
        String requestBody = """
                {
                    "name": "sony"
                }
                """;
        mockMvc.perform(put("/companies/{id}", 2).
                        contentType(MediaType.APPLICATION_JSON).
                        content(requestBody)).
                andExpect(status().isNotFound())
                .andExpect(content().string(containsString(ExceptionMsg.COMPANY_NOT_EXSITING)));
    }

    private long createCompany(String requestBody) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readTree(contentAsString).get("id").asLong();
    }
}
