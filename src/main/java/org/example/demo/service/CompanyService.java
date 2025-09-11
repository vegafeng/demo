package org.example.demo.service;

import org.example.demo.entity.Company;
import org.example.demo.exception.CompanyNotExsitingException;
import org.example.demo.resposity.CompanyRepository;
import org.example.demo.resposity.impl.CompanyRepositoryImpl;
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
//    @Autowired
//    private CompanyRepositoryImpl companyRepositoryImpl;

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
        return companyRepository.findByPage(page, size);
    }

    public Company updateCompany(Company company, long id) throws CompanyNotExsitingException {
        if (companyRepository.update(company, id)==null) throw new CompanyNotExsitingException();

        return companyRepository.update(company, id);
    }

    public void deleteCompany(long id) {
        companyRepository.delete(id);
    }

    public void clearCompanies() {
            List<Company> companies = companyRepository.findAll();
            companies.clear();
    }
}
