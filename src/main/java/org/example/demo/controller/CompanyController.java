package org.example.demo.controller;

import org.example.demo.entity.Company;
import org.example.demo.entity.Employee;
import org.example.demo.exception.CompanyNotExsitingException;
import org.example.demo.exception.PageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FENGVE
 */
@RestController
public class CompanyController {
    private List<Company> companies = new ArrayList<>();

    @PostMapping("/companies")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        if (companies.isEmpty()) {company.setId(1);}else {
            company.setId(companies.get(companies.size() - 1).getId());

        }
        companies.add(company);
        return new ResponseEntity<>(company, HttpStatus.CREATED);
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
        if (companies.size() < (page - 1) * size) {
            throw new PageNotFoundException();
        }

        return companies.stream()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }
    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> updateEmployee(@RequestBody Company company, @PathVariable long id) throws CompanyNotExsitingException {
        Company company1 = companies.stream().filter(company2 -> company2.getId() == id).findFirst().orElse(null);
        if (company1 == null) throw new CompanyNotExsitingException();
        company.setId(company1.getId());
        return new ResponseEntity<>(company, HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/companies/{id}")
    public ResponseEntity deleteEmployee(@PathVariable long id) {
        Company company = companies.stream().filter(employee2 -> employee2.getId() == id).findFirst().orElse(null);
        companies.remove(company);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void clearCompanies() {
        companies.clear();
    }


}
