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
public class Company {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    public Company(String name) {
        this.name = name;
    }
}
