package org.example.demo.controller;

import org.example.demo.entity.Company;
import org.example.demo.entity.Employee;
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
    @GetMapping(value = "/companies",params = {"page", "size"})
    public List<Company> getCompany(@RequestParam int page, @RequestParam int size) {
        List<Company> companyList=new ArrayList<>();
        if (companies.size()>=(page-1)*size){
            for (int i=(page-1)*size;i<page*size;i++) {
                if (i<companies.size()) {
                    companyList.add(companies.get(i));
                }

            }
        }
        return companyList;
    }


}
