package org.example.demo.resposity;

import org.example.demo.entity.Company;

import java.util.List;

public interface CompanyRepository {
    public Company findById(long id);
    public List<Company> findAll();
    public Company save(Company company);
    public void delete(Company company);
    public void delete(long id);
    public void deleteAll();
    public Company update(Company company, long id);
    public List<Company> findByPage(int page, int size);


}
