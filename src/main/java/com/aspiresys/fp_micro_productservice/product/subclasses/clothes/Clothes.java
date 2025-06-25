package com.aspiresys.fp_micro_productservice.product.subclasses.clothes;


import com.aspiresys.fp_micro_productservice.product.Product;

import jakarta.persistence.Entity;

import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Clothes extends Product {

    private String size;
    private String color;
    private String fabricType;
}