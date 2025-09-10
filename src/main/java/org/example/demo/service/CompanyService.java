package org.example.demo.service;

import org.example.demo.entity.Company;
import org.example.demo.exception.CompanyNotExsitingException;
import org.example.demo.exception.PageNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FENGVE
 */
@Service
public class CompanyService {
    private List<Company> companies = new ArrayList<>();

    public Company addCompany(Company company) {
        if (companies.isEmpty()) {
            company.setId(1);
        } else {
            company.setId(companies.get(companies.size() - 1).getId() + 1);
        }
        companies.add(company);
        return company;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public Company getCompanyById(int id) {
        return companies.stream().filter(company -> company.getId() == id).findFirst().orElse(null);
    }

    public List<Company> getCompaniesByPage(int page, int size) {
        if (companies.size() < (page - 1) * size) {
            throw new PageNotFoundException();
        }
        return companies.stream()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    public Company updateCompany(Company company, long id) throws CompanyNotExsitingException {
        Company existingCompany = getCompanyById((int) id);
        if (existingCompany == null) throw new CompanyNotExsitingException();
        company.setId(existingCompany.getId());
        return company;
    }

    public void deleteCompany(long id) {
        companies.removeIf(company -> company.getId() == id);
    }

    public void clearCompanies() {
        companies.clear();
    }
}
