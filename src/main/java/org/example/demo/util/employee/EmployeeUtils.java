package org.example.demo.util.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.demo.entity.Employee;

public class EmployeeUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertEmployeeToJson(Employee employee) {
        try {
            return objectMapper.writeValueAsString(employee);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}

