package com.aspiresys.fp_micro_productservice.product.subclasses.electronics.smartphone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SmartphoneServiceImpl implements SmartphoneService {
    @Autowired
    private SmartphoneRepository smartphoneRepository;

    @Override
    public Smartphone saveSmartphone(Smartphone smartphone) {
        return smartphoneRepository.save(smartphone);
    }

    @Override
    public List<Smartphone> getAllSmartphones() {
        return smartphoneRepository.findAll();
    }

    @Override
    public Smartphone getSmartphoneById(Long id) {
        Optional<Smartphone> smartphone = smartphoneRepository.findById(id);
        return smartphone.orElse(null);
    }

    @Override
    public void deleteSmartphone(Long id) {
        smartphoneRepository.deleteById(id);
    }
}
