package com.aspiresys.fp_micro_productservice.product.subclasses.electronics.smartphone;

import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.Electronics;
import jakarta.persistence.Entity;
import lombok.*;


/**
 * Represents a Smartphone entity, which is a subclass of {@link Electronics}.
 * <p>
 * This class contains properties specific to smartphones, such as operating system,
 * storage capacity, RAM, processor, and screen size.
 * </p>
 *
 * @author bruno.gil
 */
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
