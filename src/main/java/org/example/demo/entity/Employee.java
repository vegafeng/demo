package org.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * @author FENGVE
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class Employee {
//    @Id
    private long id;
    private String name;
    private int age;
    private int salary;
    private String gender;

    public Employee(String name, int age, int salary, String gender) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.gender = gender;
    }
}
