package com.e_commerce.modern.requests;

import com.e_commerce.modern.model.category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class productUpdateRequest {
    private Long id;
    private String name;
    private BigDecimal price;
    private String barnd;
    private int inventory;
    private String description;
    private com.e_commerce.modern.model.category category;
}
