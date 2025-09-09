package org.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Company {
    private int id;
    private String name;
    public Company(String name) {
        this.name = name;
    }
}
