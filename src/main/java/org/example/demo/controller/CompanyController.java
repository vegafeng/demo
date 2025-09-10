package org.example.demo.controller;

import org.example.demo.entity.Company;
import org.example.demo.entity.Employee;
import org.example.demo.exception.CompanyNotExsitingException;
import org.example.demo.exception.PageNotFoundException;
import org.example.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private CompanyService companyService;
//    private List<Company> companies = new ArrayList<>();
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> addCompany(@RequestBody Company company) {
        return new ResponseEntity<>(companyService.addCompany(company), HttpStatus.CREATED);
    }
    @GetMapping("/companies")
    public List<Company> getCompanies() {
        return companyService.getCompanies();
    }
    @GetMapping("/companies/{id}")
    public Company getCompany(@PathVariable int id) {
        return companyService.getCompanyById(id);
    }
    @GetMapping(value = "/companies",params = {"page", "size"})
    public List<Company> getCompany(@RequestParam int page, @RequestParam int size) {
       return companyService.getCompaniesByPage(page, size);
    }
    @PutMapping("/companies/{id}")
    public ResponseEntity<Company> updateEmployee(@RequestBody Company company, @PathVariable long id) throws CompanyNotExsitingException {
        return new ResponseEntity<>(companyService.updateCompany(company, id), HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/companies/{id}")
    public ResponseEntity deleteEmployee(@PathVariable long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void clearCompanies() {
        companyService.clearCompanies();
    }


}
