package com.aspiresys.fp_micro_productservice.product.subclasses.clothes;

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
 * ClothesController handles REST API endpoints for managing clothes products.
 * It provides CRUD operations for clothes category.
 *
 * Endpoints:
 * <ul>
 *   <li>POST /products/clothes - Create a new Clothes item</li>
 *   <li>GET /products/clothes - Retrieve all Clothes items</li>
 *   <li>GET /products/clothes/{id} - Retrieve a Clothes item by ID</li>
 *   <li>PUT /products/clothes/{id} - Update a Clothes item by ID</li>
 *   <li>DELETE /products/clothes/{id} - Delete a Clothes item by ID</li>
 * </ul>
 *
 * All responses are wrapped in {@link AppResponse} for consistent API responses.
 *
 * Example usage:
 * <pre>
 *   POST /products/clothes
 *   {
 *     "name": "Remera",
 *     "price": 1000.0,
 *     "category": "clothes",
 *     "imageUrl": "url",
 *     "stock": 10,
 *     "brand": "Nike",
 *     "size": "M",
 *     "color": "Azul",
 *     "fabricType": "Algodón"
 *   }
 * </pre>
 *
 * @author bruno.gil
 */
@RestController
@RequestMapping("/products/clothes")
@Log
public class ClothesController {
    
    @Autowired
    private ClothesService clothesService;

    @PostMapping
    public ResponseEntity<AppResponse<Clothes>> createClothes(@RequestBody Clothes clothes) {
        TupleResponse<Boolean, String> validation = ProductUtils.isAValidProduct(clothes);
        if (!validation.getFirst()) {
            return ResponseEntity.badRequest().body(new AppResponse<>("Invalid clothes data: " + validation.getSecond(), null));
        }
        try{
            Clothes createdClothes = clothesService.saveClothes(clothes);
            return ResponseEntity.ok(new AppResponse<>("Clothes created successfully", createdClothes));

        }catch (ProductException ex) {
            log.warning("Error creating clothes: " + ex.getMessage());
            return ResponseEntity.badRequest().body(new AppResponse<>(("Clothes with these attributes already exists: " + 
                "name=" + clothes.getName() + 
                ", category=" + clothes.getCategory() + 
                ", imageUrl=" + clothes.getImageUrl() + 
                ", price=" + clothes.getPrice() + 
                ", stock=" + clothes.getStock() + 
                ", brand=" + clothes.getBrand() + 
                ", size=" + clothes.getSize() + 
                ", color=" + clothes.getColor() + 
                ", fabricType=" + clothes.getFabricType()), null));
        }
    }

    @GetMapping
    public ResponseEntity<AppResponse<List<Clothes>>> getAllClothes() {
        List<Clothes> clothesList = clothesService.getAllClothes();
        return ResponseEntity.ok(new AppResponse<>("Clothes list retrieved successfully", clothesList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppResponse<Clothes>> getClothesById(@PathVariable Long id) {
        Clothes clothes = clothesService.getClothesById(id);
        if (clothes == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Clothes not found", null));
        }
        return ResponseEntity.ok(new AppResponse<>("Clothes found", clothes));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppResponse<Clothes>> updateClothes(@PathVariable Long id, @RequestBody Clothes clothes) {
        Clothes existing = clothesService.getClothesById(id);
        TupleResponse<Boolean, String> validation = ProductUtils.isAValidProduct(clothes);
        if (!validation.getFirst()) {
            return ResponseEntity.badRequest().body(new AppResponse<>("Invalid clothes data: " + validation.getSecond(), null));
        }
        if (existing == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Clothes not found", null));
        }
        clothes.setId(id);
        Clothes updated = clothesService.saveClothes(clothes);
        return ResponseEntity.ok(new AppResponse<>("Clothes updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppResponse<Boolean>> deleteClothes(@PathVariable Long id) {
        Clothes existing = clothesService.getClothesById(id);
        if (existing == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Clothes not found", false));
        }
        clothesService.deleteClothes(id);
        return ResponseEntity.ok(new AppResponse<>("Clothes deleted successfully", true));
    }
}
