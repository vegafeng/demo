package org.example.demo.resposity.impl;

import org.example.demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author FENGVE
 */
@Repository
public interface EmployeeRepositoryImpl extends JpaRepository<Employee, Long> {

}
