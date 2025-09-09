package org.example.demo.controller;

import org.example.demo.entity.Company;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/companies")
    public List<Company> getCompanies() {
        return companies;
    }
    @GetMapping("/companies/{id}")
    public Company getCompany(@PathVariable int id) {
        return  companies.stream().filter(company -> company.getId() == id).findFirst().orElse(null);
    }

}
