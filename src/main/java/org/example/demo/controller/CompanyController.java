package org.example.demo.controller;

import org.example.demo.entity.Company;
import org.example.demo.entity.Employee;
import org.example.demo.exception.PageNotFoundException;
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
        if (companies.size()<(page-1)*size) throw new PageNotFoundException();
        for (int i=(page-1)*size;i<page*size;i++) {
                if (i<companies.size()) {
                    companyList.add(companies.get(i));
                }

        }

        return companyList;
    }
    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> updateEmployee(@RequestBody Company company, @PathVariable long id) {
        Company company1 = companies.stream().filter(company2 -> company2.getId() == id).findFirst().orElse(null);
        if (company1 != null) {
            company.setId(company1.getId());
            return new ResponseEntity(company, HttpStatus.NO_CONTENT);
        }
        throw new RuntimeException();
    }
    @DeleteMapping("/companies/{id}")
    public ResponseEntity deleteEmployee(@PathVariable long id) {
        Company company = companies.stream().filter(employee2 -> employee2.getId() == id).findFirst().orElse(null);
        companies.remove(company);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public void clearCompanies() {
        companies.clear();
    }


}
