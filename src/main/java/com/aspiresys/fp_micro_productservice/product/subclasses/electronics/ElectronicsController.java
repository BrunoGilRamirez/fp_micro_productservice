package com.aspiresys.fp_micro_productservice.product.subclasses.electronics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.aspiresys.fp_micro_productservice.common.dto.AppResponse;
import com.aspiresys.fp_micro_productservice.product.ProductUtils;
import com.aspiresys.fp_micro_productservice.product.ProductUtils.TupleResponse;
import com.aspiresys.fp_micro_productservice.product.ProductException;

import lombok.extern.java.Log;

/**
 * ElectronicsController handles REST API endpoints for managing electronics products.
 * It provides CRUD operations for electronics category.
 *
 * Endpoints:
 * <ul>
 *   <li>POST /products/electronics - Create a new Electronics item</li>
 *   <li>GET /products/electronics - Retrieve all Electronics items</li>
 *   <li>GET /products/electronics/{id} - Retrieve an Electronics item by ID</li>
 *   <li>PUT /products/electronics/{id} - Update an Electronics item by ID</li>
 *   <li>DELETE /products/electronics/{id} - Delete an Electronics item by ID</li>
 * </ul>
 *
 * All responses are wrapped in {@link AppResponse} for consistent API responses.
 *
 * Example usage:
 * <pre>
 *   POST /products/electronics
 *   {
 *     "name": "Laptop",
 *     "price": 1500.0,
 *     "category": "electronics",
 *     "imageUrl": "url",
 *     "stock": 3,
 *     "brand": "Dell",
 *     "model": "XPS 13",
 *     "warrantyPeriod": "24 months"
 *   }
 * </pre>
 *
 * @author bruno.gil
 */
@RestController
@RequestMapping("/products/electronics")
@Log
public class ElectronicsController {
    
    @Autowired
    private ElectronicsService electronicsService;

    @PostMapping
    public ResponseEntity<AppResponse<Electronics>> createElectronics(@RequestBody Electronics electronics) {
        electronics.setCategory("electronics"); // Ensure category is set
        TupleResponse<Boolean, String> validation = ProductUtils.isAValidProduct(electronics);
        if (!validation.getFirst()) {
            return ResponseEntity.badRequest().body(new AppResponse<>("Invalid electronics data: " + validation.getSecond(), null));
        }
        try {
            Electronics created = electronicsService.saveElectronics(electronics);
            return ResponseEntity.ok(new AppResponse<>("Electronics created successfully", created));
        } catch (ProductException ex) {
            log.warning("Error creating electronics: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new AppResponse<>(("Electronics with these attributes already exists: " + 
                "name=" + electronics.getName() + 
                ", category=" + electronics.getCategory() + 
                ", imageUrl=" + electronics.getImageUrl() + 
                ", brand=" + electronics.getBrand() + 
                ", model=" + electronics.getModel() + 
                ", warrantyPeriod=" + electronics.getWarrantyPeriod()), null));
        }
    }

    @GetMapping
    public ResponseEntity<AppResponse<List<Electronics>>> getAllElectronics() {
        List<Electronics> list = electronicsService.getAllElectronics();
        return ResponseEntity.ok(new AppResponse<>("Electronics list retrieved successfully", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppResponse<Electronics>> getElectronicsById(@PathVariable Long id) {
        Electronics electronics = electronicsService.getElectronicsById(id);
        if (electronics == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Electronics not found", null));
        }
        return ResponseEntity.ok(new AppResponse<>("Electronics found", electronics));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppResponse<Electronics>> updateElectronics(@PathVariable Long id, @RequestBody Electronics electronics) {
        Electronics existing = electronicsService.getElectronicsById(id);
        TupleResponse<Boolean, String> validation = ProductUtils.isAValidProduct(electronics);
        if (!validation.getFirst()) {
            return ResponseEntity.badRequest().body(new AppResponse<>("Invalid electronics data: " + validation.getSecond(), null));
        }
        if (existing == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Electronics not found", null));
        }
        electronics.setId(id);
        Electronics updated = electronicsService.saveElectronics(electronics);
        return ResponseEntity.ok(new AppResponse<>("Electronics updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppResponse<Boolean>> deleteElectronics(@PathVariable Long id) {
        Electronics existing = electronicsService.getElectronicsById(id);
        if (existing == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Electronics not found", false));
        }
        electronicsService.deleteElectronics(id);
        return ResponseEntity.ok(new AppResponse<>("Electronics deleted successfully", true));
    }
}
