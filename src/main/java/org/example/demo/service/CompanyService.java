package org.example.demo.service;

import org.example.demo.entity.Company;
import org.example.demo.exception.CompanyNotExsitingException;
import org.example.demo.exception.PageNotFoundException;
import org.example.demo.resposity.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FENGVE
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company addCompany(Company company) {

        companyRepository.save(company);
        return company;
    }

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(long id) {
        return companyRepository.findById(id);
    }

    public List<Company> getCompaniesByPage(int page, int size) {
        List<Company> byPage = companyRepository.findByPage(page, size);
        if (byPage.isEmpty()) {
            throw new PageNotFoundException();
        }
        return byPage;
    }

    public Company updateCompany(Company company, long id) throws CompanyNotExsitingException {
        if (companyRepository.findById(id)==null) throw new CompanyNotExsitingException();

        return companyRepository.update(company, id);
    }

    public void deleteCompany(long id) {
        companyRepository.delete(id);
    }

    public void clearCompanies() {
            companyRepository.deleteAll();
    }
}
