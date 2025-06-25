package com.aspiresys.fp_micro_productservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import com.aspiresys.fp_micro_productservice.common.dto.AppResponse;
import com.aspiresys.fp_micro_productservice.product.subclasses.clothes.Clothes;
import com.aspiresys.fp_micro_productservice.product.subclasses.clothes.ClothesService;
import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.Electronics;
import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.ElectronicsService;
import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.smartphone.Smartphone;
import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.smartphone.SmartphoneService;

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

    // CRUD para Electronics
    @PostMapping("/electronics")
    public ResponseEntity<AppResponse<Electronics>> createElectronics(@RequestBody Electronics electronics) {
        Electronics created = electronicsService.saveElectronics(electronics);
        return ResponseEntity.ok(new AppResponse<>("Electronics created successfully", created));
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

    @PutMapping("/electronics/{id}")
    public ResponseEntity<AppResponse<Electronics>> updateElectronics(@PathVariable Long id, @RequestBody Electronics electronics) {
        Electronics existing = electronicsService.getElectronicsById(id);
        if (existing == null) {
            return ResponseEntity.status(404).body(new AppResponse<>("Electronics not found", null));
        }
        electronics.setId(id);
        Electronics updated = electronicsService.saveElectronics(electronics);
        return ResponseEntity.ok(new AppResponse<>("Electronics updated successfully", updated));
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
        Smartphone created = smartphoneService.saveSmartphone(smartphone);
        return ResponseEntity.ok(new AppResponse<>("Smartphone created successfully", created));
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

    // Endpoint para obtener ejemplos de bodys aceptados
    @GetMapping("/body-examples")
    public ResponseEntity<AppResponse<Map<String, Object>>> getBodyExamples() {
        Map<String, Object> examples = new HashMap<>();
        examples.put("clothes", Map.of(
            "name", "Remera",
            "price", 1000.0,
            "category", "clothes",
            "imageUrl", "url",
            "stock", 10,
            "size", "M",
            "color", "Azul",
            "fabricType", "Algodón"
        ));
        examples.put("electronics", Map.of(
            "name", "Televisor",
            "price", 50000.0,
            "category", "electronics",
            "imageUrl", "url",
            "stock", 5,
            "brand", "Samsung",
            "model", "QLED",
            "warrantyPeriod", "2 años",
            "specifications", "4K UHD"
        ));
        Map<String, Object> smartphoneExample = new HashMap<>();
        smartphoneExample.put("name", "iPhone 15");
        smartphoneExample.put("price", 120000.0);
        smartphoneExample.put("category", "smartphone");
        smartphoneExample.put("imageUrl", "url");
        smartphoneExample.put("stock", 3);
        smartphoneExample.put("brand", "Apple");
        smartphoneExample.put("model", "15 Pro");
        smartphoneExample.put("warrantyPeriod", "1 año");
        smartphoneExample.put("specifications", "128GB");
        smartphoneExample.put("operatingSystem", "iOS");
        smartphoneExample.put("storageCapacity", 128);
        smartphoneExample.put("ram", 6);
        smartphoneExample.put("processor", "A17");
        smartphoneExample.put("screenSize", 6.1);
        examples.put("smartphone", smartphoneExample);
        return ResponseEntity.ok(new AppResponse<>("Body examples retrieved successfully", examples));
    }
}
