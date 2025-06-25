package com.aspiresys.fp_micro_productservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

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
    public Clothes createClothes(@RequestBody Clothes clothes) {
        return clothesService.saveClothes(clothes);
    }

    @GetMapping("/clothes")
    public List<Clothes> getAllClothes() {
        return clothesService.getAllClothes();
    }

    @GetMapping("/clothes/{id}")
    public Clothes getClothesById(@PathVariable Long id) {
        return clothesService.getClothesById(id);
    }

    @PutMapping("/clothes/{id}")
    public Clothes updateClothes(@PathVariable Long id, @RequestBody Clothes clothes) {
        Clothes existing = clothesService.getClothesById(id);
        if (existing == null) return null;
        clothes.setId(id);
        return clothesService.saveClothes(clothes);
    }

    @DeleteMapping("/clothes/{id}")
    public void deleteClothes(@PathVariable Long id) {
        clothesService.deleteClothes(id);
    }

    // CRUD para Electronics
    @PostMapping("/electronics")
    public Electronics createElectronics(@RequestBody Electronics electronics) {
        return electronicsService.saveElectronics(electronics);
    }

    @GetMapping("/electronics")
    public List<Electronics> getAllElectronics() {
        return electronicsService.getAllElectronics();
    }

    @GetMapping("/electronics/{id}")
    public Electronics getElectronicsById(@PathVariable Long id) {
        return electronicsService.getElectronicsById(id);
    }

    @PutMapping("/electronics/{id}")
    public Electronics updateElectronics(@PathVariable Long id, @RequestBody Electronics electronics) {
        Electronics existing = electronicsService.getElectronicsById(id);
        if (existing == null) return null;
        electronics.setId(id);
        return electronicsService.saveElectronics(electronics);
    }

    @DeleteMapping("/electronics/{id}")
    public void deleteElectronics(@PathVariable Long id) {
        electronicsService.deleteElectronics(id);
    }

    // CRUD para Smartphone
    @PostMapping("/smartphones")
    public Smartphone createSmartphone(@RequestBody Smartphone smartphone) {
        return smartphoneService.saveSmartphone(smartphone);
    }

    @GetMapping("/smartphones")
    public List<Smartphone> getAllSmartphones() {
        return smartphoneService.getAllSmartphones();
    }

    @GetMapping("/smartphones/{id}")
    public Smartphone getSmartphoneById(@PathVariable Long id) {
        return smartphoneService.getSmartphoneById(id);
    }

    @PutMapping("/smartphones/{id}")
    public Smartphone updateSmartphone(@PathVariable Long id, @RequestBody Smartphone smartphone) {
        Smartphone existing = smartphoneService.getSmartphoneById(id);
        if (existing == null) return null;
        smartphone.setId(id);
        return smartphoneService.saveSmartphone(smartphone);
    }

    @DeleteMapping("/smartphones/{id}")
    public void deleteSmartphone(@PathVariable Long id) {
        smartphoneService.deleteSmartphone(id);
    }

    // Obtener todos los productos (de cualquier categoría)
    @GetMapping("")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Endpoint para obtener todas las categorías disponibles
    @GetMapping("/categories")
    public List<String> getCategories() {
        return Arrays.asList("clothes", "electronics", "smartphone");
    }

    // Endpoint para obtener ejemplos de bodys aceptados
    @GetMapping("/body-examples")
    public Map<String, Object> getBodyExamples() {
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
        return examples;
    }
}
