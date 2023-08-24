package com.project.spring.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "manufacture")
public class Manufacture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "manufacture", fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<Product> products;

    public Manufacture(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
