package org.example.demo.controller;

import org.example.demo.entity.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CompanyController {
    private List<Company> companies = new ArrayList<Company>();

    @PostMapping("/companies")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        company.setId(companies.size()+1);
        companies.add(company);
        return new ResponseEntity(company, HttpStatus.CREATED);
    }


}
