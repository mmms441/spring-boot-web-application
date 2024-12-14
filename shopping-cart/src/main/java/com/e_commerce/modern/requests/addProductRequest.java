package com.e_commerce.modern.requests;

import com.e_commerce.modern.model.category;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data

public class addProductRequest {
    private Long id;
    private String name;
    private BigDecimal price;
    private String barnd;
    private int inventory;
    private String description;
    private category category;

}
