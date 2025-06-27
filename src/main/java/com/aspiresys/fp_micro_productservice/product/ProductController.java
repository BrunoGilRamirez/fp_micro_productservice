package com.aspiresys.fp_micro_productservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Arrays;

import com.aspiresys.fp_micro_productservice.common.dto.AppResponse;
import com.aspiresys.fp_micro_productservice.product.ProductUtils.TupleResponse;
import com.aspiresys.fp_micro_productservice.product.subclasses.clothes.Clothes;
import com.aspiresys.fp_micro_productservice.product.subclasses.clothes.ClothesService;
import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.Electronics;
import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.ElectronicsService;
import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.smartphone.Smartphone;
import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.smartphone.SmartphoneService;

/**
 * ProductController handles REST API endpoints for managing products in different categories:
 * Clothes, Electronics, and Smartphones. It provides CRUD operations for each category,
 * as well as endpoints to retrieve all products, available categories, and example request bodies.
 *
 * Endpoints:
 * <ul>
 *   <li><b>Clothes:</b>
 *     <ul>
 *       <li>POST /products/clothes - Create a new Clothes item</li>
 *       <li>GET /products/clothes - Retrieve all Clothes items</li>
 *       <li>GET /products/clothes/{id} - Retrieve a Clothes item by ID</li>
 *       <li>PUT /products/clothes/{id} - Update a Clothes item by ID</li>
 *       <li>DELETE /products/clothes/{id} - Delete a Clothes item by ID</li>
 *     </ul>
 *   </li>
 *   <li><b>Electronics:</b>
 *     <ul>
 *       <li>POST /products/electronics - Create a new Electronics item</li>
 *       <li>GET /products/electronics - Retrieve all Electronics items</li>
 *       <li>GET /products/electronics/{id} - Retrieve an Electronics item by ID</li>
 *       <li>PUT /products/electronics/{id} - Update an Electronics item by ID</li>
 *       <li>DELETE /products/electronics/{id} - Delete an Electronics item by ID</li>
 *     </ul>
 *   </li>
 *   <li><b>Smartphones:</b>
 *     <ul>
 *       <li>POST /products/smartphones - Create a new Smartphone item</li>
 *       <li>GET /products/smartphones - Retrieve all Smartphone items</li>
 *       <li>GET /products/smartphones/{id} - Retrieve a Smartphone item by ID</li>
 *       <li>PUT /products/smartphones/{id} - Update a Smartphone item by ID</li>
 *       <li>DELETE /products/smartphones/{id} - Delete a Smartphone item by ID</li>
 *     </ul>
 *   </li>   *   <li><b>General:</b>
 *     <ul>
 *       <li>GET /products - Retrieve all products (any category)</li>
 *       <li>GET /products/categories - Retrieve available product categories</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * All responses are wrapped in {@link AppResponse} for consistent API responses.
 *
 * Services injected:
 * <ul>
 *   <li>{@link ElectronicsService}</li>
 *   <li>{@link SmartphoneService}</li>
 *   <li>{@link ClothesService}</li>
 *   <li>{@link ProductService}</li>
 * </ul>
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
 *     "size": "M",
 *     "color": "Azul",
 *     "fabricType": "Algodón"
 *   }
 * </pre>
 *
 * @author bruno.gil
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ElectronicsService electronicsService;
    @Autowired
    private SmartphoneService smartphoneService;
    @Autowired
    private ClothesService clothesService;
    @Autowired
    private ProductService productService;

    // CRUD para Clothes
    @PostMapping("/clothes")
    public ResponseEntity<AppResponse<Clothes>> createClothes(@RequestBody Clothes clothes) {
        TupleResponse<Boolean, String> validation = ProductUtils.isAValidProduct(clothes);
        if (!validation.getFirst()) {
            return ResponseEntity.badRequest().body(new AppResponse<>("Invalid clothes data: " + validation.getSecond(), null));
        }
        if(clothesService.exist(clothes)){
            return ResponseEntity.badRequest().body(new AppResponse<>("Clothes with these attributes already exists.", null));
        }
        Clothes createdClothes = clothesService.saveClothes(clothes);
        return ResponseEntity.ok(new AppResponse<>("Clothes created successfully", createdClothes));
    }

    @GetMapping("/clothes")
    public ResponseEntity<AppResponse<List<Clothes>>> getAllClothes() {
        List<Clothes> clothesList = clothesService.getAllClothes();
        return ResponseEntity.ok(new AppResponse<>("Clothes list retrieved successfully", clothesList));
    }

    @GetMapping("/clothes/{id}")
    public ResponseEntity<AppResponse<Clothes>> getClothesById(@PathVariable Long id) {
        Clothes clothes = clothesService.getClothesById(id);
        if (clothes == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Clothes not found", null));
        }
        return ResponseEntity.ok(new AppResponse<>("Clothes found", clothes));
    }

    @PutMapping("/clothes/{id}")
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

    @DeleteMapping("/clothes/{id}")
    public ResponseEntity<AppResponse<Boolean>> deleteClothes(@PathVariable Long id) {
        Clothes existing = clothesService.getClothesById(id);
        if (existing == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Clothes not found", false));
        }
        clothesService.deleteClothes(id);
        return ResponseEntity.ok(new AppResponse<>("Clothes deleted successfully", true));
    }


    @GetMapping("/electronics")
    public ResponseEntity<AppResponse<List<Electronics>>> getAllElectronics() {
        List<Electronics> list = electronicsService.getAllElectronics();
        return ResponseEntity.ok(new AppResponse<>("Electronics list retrieved successfully", list));
    }

    @GetMapping("/electronics/{id}")
    public ResponseEntity<AppResponse<Electronics>> getElectronicsById(@PathVariable Long id) {
        Electronics electronics = electronicsService.getElectronicsById(id);
        if (electronics == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Electronics not found", null));
        }
        return ResponseEntity.ok(new AppResponse<>("Electronics found", electronics));
    }

    @DeleteMapping("/electronics/{id}")
    public ResponseEntity<AppResponse<Boolean>> deleteElectronics(@PathVariable Long id) {
        Electronics existing = electronicsService.getElectronicsById(id);
        if (existing == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Electronics not found", false));
        }
        electronicsService.deleteElectronics(id);
        return ResponseEntity.ok(new AppResponse<>("Electronics deleted successfully", true));
    }

    // CRUD para Smartphone
    @PostMapping("/smartphones")
    public ResponseEntity<AppResponse<Smartphone>> createSmartphone(@RequestBody Smartphone smartphone) {
        smartphone.setCategory("smartphone"); // Ensure category is set
        TupleResponse<Boolean, String> validation = ProductUtils.isAValidProduct(smartphone);
        if (!validation.getFirst()) {
            return ResponseEntity.badRequest().body(new AppResponse<>("Invalid smartphone data: " + validation.getSecond(), null));
        }
        if(smartphoneService.exists(smartphone)){
            return ResponseEntity.badRequest().body(new AppResponse<>("Smartphone with these attributes already exists.", null));
        }
        try {
            Smartphone created = smartphoneService.saveSmartphone(smartphone);
            return ResponseEntity.ok(new AppResponse<>("Smartphone created successfully", created));
        }catch (DataIntegrityViolationException ex) {
            return ResponseEntity.badRequest().body(new AppResponse<>("Ya existe un smartphone con esa combinación de atributos.", null));
        }
    }

    @GetMapping("/smartphones")
    public ResponseEntity<AppResponse<List<Smartphone>>> getAllSmartphones() {
        List<Smartphone> list = smartphoneService.getAllSmartphones();
        return ResponseEntity.ok(new AppResponse<>("Smartphone list retrieved successfully", list));
    }

    @GetMapping("/smartphones/{id}")
    public ResponseEntity<AppResponse<Smartphone>> getSmartphoneById(@PathVariable Long id) {
        Smartphone smartphone = smartphoneService.getSmartphoneById(id);
        if (smartphone == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Smartphone not found", null));
        }
        return ResponseEntity.ok(new AppResponse<>("Smartphone found", smartphone));
    }

    @PutMapping("/smartphones/{id}")
    public ResponseEntity<AppResponse<Smartphone>> updateSmartphone(@PathVariable Long id, @RequestBody Smartphone smartphone) {
        Smartphone existing = smartphoneService.getSmartphoneById(id);
        if (existing == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Smartphone not found", null));
        }
        smartphone.setId(id);
        Smartphone updated = smartphoneService.saveSmartphone(smartphone);
        return ResponseEntity.ok(new AppResponse<>("Smartphone updated successfully", updated));
    }

    @DeleteMapping("/smartphones/{id}")
    public ResponseEntity<AppResponse<Boolean>> deleteSmartphone(@PathVariable Long id) {
        Smartphone existing = smartphoneService.getSmartphoneById(id);
        if (existing == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Smartphone not found", false));
        }
        smartphoneService.deleteSmartphone(id);
        return ResponseEntity.ok(new AppResponse<>("Smartphone deleted successfully", true));
    }

    // Obtener todos los productos (de cualquier categoría)
    @GetMapping("")
    public ResponseEntity<AppResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new AppResponse<>("Product list retrieved successfully", products));
    }

    // Endpoint para obtener todas las categorías disponibles
    @GetMapping("/categories")
    public ResponseEntity<AppResponse<List<String>>> getCategories() {
        List<String> categories = Arrays.asList("clothes", "electronics", "smartphone");
        return ResponseEntity.ok(new AppResponse<>("Categories retrieved successfully", categories));
    }

}
