package org.example.demo.resposity.jpa;

import org.example.demo.entity.Employee;
import org.example.demo.resposity.EmployeeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {

}
