package org.example.demo;

import org.example.demo.controller.CompanyController;
import org.example.demo.entity.Company;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyController companyController;
    private Company company = new Company("cosco");
    private Company company2 = new Company("oocl");
    private Company company3 = new Company("sony");
    private Company company4 = new Company("sam");
    @BeforeEach
    public void setUp() {
        companyController.clearCompanies();
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
                andExpect(status().isCreated()).
                andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("cosco"));
    }
    @Test
    public void should_return_companies_when_get_all_given_null() throws Exception{
        companyController.addCompany(company);
        companyController.addCompany(company2);
        mockMvc.perform(get("/companies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(company.getId()))
                .andExpect(jsonPath("$[0].name").value(company.getName()))
                .andExpect(jsonPath("$[1].id").value(company2.getId()))
                .andExpect(jsonPath("$[1].name").value(company2.getName()));
    }

    @Test
    public void should_return_employees_when_get_given_id() throws Exception {
        companyController.addCompany(company);
        mockMvc.perform(get("/companies/{id}", 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(company.getId()))
                .andExpect(jsonPath("$.name").value(company.getName()));
    }
    @Test
    public void should_return_companies_when_get_by_page_given_page_size() throws Exception {
        companyController.addCompany(company);
        companyController.addCompany(company2);
        companyController.addCompany(company3);
        companyController.addCompany(company4);
        mockMvc.perform(get("/companies?page=1&size=5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(company.getId()))
                .andExpect(jsonPath("$[0].name").value(company.getName()))
                .andExpect(jsonPath("$.length()").value(4));
    }
    @Test
    public void should_return_matching_code_when_update_by_id_given_age_salary() throws Exception {
        companyController.addCompany(company);
        String requestBody = """
                {
                    "name": "oocl"
                }
                """;
        mockMvc.perform(put("/companies/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("oocl"));
    }
    @Test
    public void should_response_no_content_when_delete_given_employee_id() throws Exception {
        companyController.addCompany(company);
        mockMvc.perform(delete("/companies/{id}", 1)
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
                .andExpect(content().string(containsString(ExceptionMsg.Page_Not_Found)));
    }
}
