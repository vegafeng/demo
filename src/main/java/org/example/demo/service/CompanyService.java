package org.example.demo.service;

import org.example.demo.entity.Company;
import org.example.demo.exception.CompanyNotExsitingException;
import org.example.demo.resposity.CompanyResposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @author FENGVE
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyResposity companiesResposity;
    @Autowired
    private CompanyResposity companyResposity;

    public Company addCompany(Company company) {

        companiesResposity.save(company);
        return company;
    }

    public List<Company> getCompanies() {
        return companiesResposity.findAll();
    }

    public Company getCompanyById(long id) {
        return companiesResposity.findById(id);
    }

    public List<Company> getCompaniesByPage(int page, int size) {
        return companiesResposity.findByPage(page, size);
    }

    public Company updateCompany(Company company, long id) throws CompanyNotExsitingException {
        if (companyResposity.update(company, id)==null) throw new CompanyNotExsitingException();

        return companyResposity.update(company, id);
    }

    public void deleteCompany(long id) {
        companiesResposity.delete(id);
    }

    public void clearCompanies() {
            List<Company> companies = companyResposity.findAll();
            companies.clear();
    }
}
