package com.aspiresys.fp_micro_productservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Arrays;

import com.aspiresys.fp_micro_productservice.common.dto.AppResponse;

import lombok.extern.java.Log;

/**
 * ProductController handles general REST API endpoints for managing products.
 * It provides endpoints to retrieve all products and available categories.
 * 
 * Specific CRUD operations for each product type are now handled by dedicated controllers:
 * - {@link com.aspiresys.fp_micro_productservice.product.subclasses.clothes.ClothesController}
 * - {@link com.aspiresys.fp_micro_productservice.product.subclasses.electronics.ElectronicsController}
 * - {@link com.aspiresys.fp_micro_productservice.product.subclasses.electronics.smartphone.SmartphoneController}
 *
 * Endpoints:
 * <ul>
 *   <li><b>General:</b>
 *     <ul>
 *       <li>GET /products - Retrieve all products (any category)</li>
 *       <li>GET /products/categories - Retrieve available product categories</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * All responses are wrapped in {@link AppResponse} for consistent API responses.
 *
 * @author bruno.gil
 */
@RestController
@RequestMapping("/products")
@Log
public class ProductController {
    
    @Autowired
    private ProductService productService;

    /**
     * This endpoint retrieves all products available in the system.
     * <p>
     * This endpoint must be public and accessible without authentication. When auth is implemented for this application,
     * it should be accessible to all users, including unauthenticated ones.
     * @return ResponseEntity containing a list of all products wrapped in AppResponse.
     * </p>
     */
    @GetMapping("")
    public ResponseEntity<AppResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new AppResponse<>("Product list retrieved successfully", products));
    }

    /**
     * This endpoint retrieves all available product categories.
     * 
     * <p>
     * This endpoint must be public and accessible without authentication. When auth is implemented for this application,
     * it should be accessible to all users, including unauthenticated ones.
     * @return ResponseEntity containing a list of product categories wrapped in AppResponse.
     * </p>
     */
    @GetMapping("/categories")
    public ResponseEntity<AppResponse<List<String>>> getCategories() {
        List<String> categories = Arrays.asList("clothes", "electronics", "smartphone");
        return ResponseEntity.ok(new AppResponse<>("Categories retrieved successfully", categories));
    }

}
