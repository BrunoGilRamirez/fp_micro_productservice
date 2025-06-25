package com.aspiresys.fp_micro_productservice.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    private ProductService productService;
    @Autowired
    private ElectronicsService electronicsService;
    @Autowired
    private SmartphoneService smartphoneService;
    @Autowired
    private ClothesService clothesService;

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
}
