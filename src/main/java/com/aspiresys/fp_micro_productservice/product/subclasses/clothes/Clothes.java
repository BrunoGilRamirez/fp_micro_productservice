package com.aspiresys.fp_micro_productservice.product.subclasses.clothes;


import com.aspiresys.fp_micro_productservice.product.Product;

import jakarta.persistence.Entity;

import lombok.*;


/**
 * Represents a clothing product with specific attributes such as size, color, and fabric type.
 * Inherits common product properties from the {@link Product} class.
 * <p>
 * This entity is mapped to a database table for persistence.
 * </p>
 *
 * @author bruno.gil
 */
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