package com.e_commerce.modern.model;

import jakarta.persistence.*;
import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private String brand;

    private int inventory;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="category_id")
    private category category;
    @OneToMany(mappedBy = "product" ,cascade = CascadeType.ALL , orphanRemoval = true)
    private List<image> images;

    public product(String name, BigDecimal price, String brand, int inventory, String description,category category) {
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.inventory = inventory;
        this.description = description;
        this.category = category;
    }
}
