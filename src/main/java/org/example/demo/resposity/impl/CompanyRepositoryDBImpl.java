package org.example.demo.resposity.impl;

import org.example.demo.entity.Company;
import org.example.demo.entity.Employee;
import org.example.demo.resposity.CompanyRepository;
import org.example.demo.resposity.jpa.CompanyJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FENGVE
 */
@Repository
public class CompanyRepositoryDBImpl implements CompanyRepository {
    @Autowired
    private CompanyJpaRepository companyJpaRepository;
    @Override
    public Company findById(long id) {
        return companyJpaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Company> findAll() {
        return companyJpaRepository.findAll();
    }

    @Override
    public Company save(Company company) {
        return companyJpaRepository.save(company);
    }

    @Override
    public void delete(Company company) {
        companyJpaRepository.delete(company);
    }

    @Override
    public void delete(long id) {
        companyJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        companyJpaRepository.deleteAll();
    }

    @Override
    public Company update(Company company, long id) {
//        Company old = findById(id);
//        old.setName(company.getName());
//        old.setEmployees(company.getEmployees());
        return companyJpaRepository.save(company );
    }

    @Override
    public List<Company> findByPage(int page, int size) {
        Page<Company> companies = companyJpaRepository.findAll(PageRequest.of(page, size));
        return companies.getContent();
    }
}
