package org.example.demo;

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
}
