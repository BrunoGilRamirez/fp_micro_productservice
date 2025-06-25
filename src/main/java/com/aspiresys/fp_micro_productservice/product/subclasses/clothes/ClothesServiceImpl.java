package com.aspiresys.fp_micro_productservice.product.subclasses.clothes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClothesServiceImpl implements ClothesService {
    @Autowired
    private ClothesRepository clothesRepository;

    @Override
    public Clothes saveClothes(Clothes clothes) {
        return clothesRepository.save(clothes);
    }

    @Override
    public List<Clothes> getAllClothes() {
        return clothesRepository.findAll();
    }

    @Override
    public Clothes getClothesById(Long id) {
        Optional<Clothes> clothes = clothesRepository.findById(id);
        return clothes.orElse(null);
    }

    @Override
    public void deleteClothes(Long id) {
        clothesRepository.deleteById(id);
    }
}
