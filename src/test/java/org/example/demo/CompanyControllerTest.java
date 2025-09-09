package org.example.demo;

import org.example.demo.controller.CompanyController;
import org.example.demo.entity.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("cosco"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("oocl"));
    }
}
