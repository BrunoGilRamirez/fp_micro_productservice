package com.aspiresys.fp_micro_productservice.product.subclasses.electronics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ElectronicsServiceImpl implements ElectronicsService {
    @Autowired
    private ElectronicsRepository electronicsRepository;

    @Override
    public Electronics saveElectronics(Electronics electronics) {
        return electronicsRepository.save(electronics);
    }

    @Override
    public List<Electronics> getAllElectronics() {
        return electronicsRepository.findAll();
    }

    @Override
    public Electronics getElectronicsById(Long id) {
        Optional<Electronics> electronics = electronicsRepository.findById(id);
        return electronics.orElse(null);
    }

    @Override
    public void deleteElectronics(Long id) {
        electronicsRepository.deleteById(id);
    }
}
