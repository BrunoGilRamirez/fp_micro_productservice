package com.aspiresys.fp_micro_productservice.product.subclasses.electronics.smartphone;

import java.util.List;

public interface SmartphoneService {
    Smartphone saveSmartphone(Smartphone smartphone);
    List<Smartphone> getAllSmartphones();
    Smartphone getSmartphoneById(Long id);
    void deleteSmartphone(Long id);
}
