package com.aspiresys.fp_micro_productservice.product.subclasses.electronics.smartphone;

import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.Electronics;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Smartphone extends Electronics {
    private String operatingSystem;
    private int storageCapacity;
    private int ram;
    private String processor;
    private double screenSize;
}
