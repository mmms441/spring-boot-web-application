package com.e_commerce.modern.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    //Try to use this instead   ->"@Lob private byte[] image;"
    @Lob
    private Blob image;
    private String downloadUrl;


    //private relationship
    @ManyToOne
    @JoinColumn(name = "product_id")
    private product product;
}
