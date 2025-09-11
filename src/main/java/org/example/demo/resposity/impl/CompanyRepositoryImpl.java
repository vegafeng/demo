package org.example.demo.resposity.impl;

import org.example.demo.entity.Company;
import org.example.demo.exception.PageNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class CompanyRepositoryImpl {
    private List<Company> companies = new ArrayList<>();

    public List<Company> findAll() {
        return companies;
    }

    public Company findById(long id) {
        return companies.stream().filter(company -> company.getId() == id).findFirst().orElse(null);
    }
    public Company save(Company company) {
        if (companies.isEmpty()) {
            company.setId(1);
        } else {
            company.setId(companies.get(companies.size() - 1).getId() + 1);
        }
        companies.add(company);
        return company;
    }
    public Company update(Company company, long id) {
        if (id <=0 || id > companies.get(companies.size() - 1).getId()) {
            return null;
        }
        Company updateCompany = findById(id);
        updateCompany.setName(company.getName());

        return updateCompany;
    }
    public Company delete(long id) {
        int index = companies.indexOf(findById(id));
        if (index != -1) {
            companies.remove(index);
        }
        return findById(id);
    }
    public List<Company> findByPage(int page, int size) {
        if (companies.size() < (page - 1) * size) {
            throw new PageNotFoundException();
        }
        return companies.stream()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }

}
