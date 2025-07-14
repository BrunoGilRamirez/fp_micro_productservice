package com.aspiresys.fp_micro_productservice.aop;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import com.aspiresys.fp_micro_productservice.product.Product;
import com.aspiresys.fp_micro_productservice.product.ProductRepository;
import com.aspiresys.fp_micro_productservice.product.ProductService;
import com.aspiresys.fp_micro_productservice.product.subclasses.electronics.smartphone.Smartphone;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Test de integración para verificar que AOP funciona correctamente
 * en el servicio de productos. Usa el contexto completo de Spring
 * para que los aspectos funcionen correctamente.
 * 
 * @author bruno.gil
 */
@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "app.aop.audit.enabled=true",
    "app.aop.performance.enabled=true", 
    "app.aop.validation.enabled=true",
    "spring.aop.auto=true",
    "spring.aop.proxy-target-class=true"
})
public class AopIntegrationTest {

    @SuppressWarnings("removal")
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    void testAopAnnotationsOnSaveProduct() {
        // Arrange
        Smartphone testProduct = createMockSmartphone(1L, "Test Smartphone", 999.99);
        
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // Act - Este método debería activar los aspectos AOP
        Product result = productService.saveProduct(testProduct);

        // Assert
        assertNotNull(result, "Product should be saved successfully");
        assertEquals("Test Smartphone", result.getName(), "Product name should match");
        assertEquals(999.99, result.getPrice(), "Product price should match");

        // Los aspectos AOP deberían haber sido ejecutados:
        // 1. @Auditable - debería haber registrado la operación de guardado
        // 2. @ExecutionTime - debería haber medido el tiempo de ejecución  
        // 3. @ValidateParameters - debería haber validado que el product no es null

        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    void testAopAnnotationsOnGetAllProducts() {
        // Arrange
        List<Product> mockProducts = Arrays.asList(
            createMockSmartphone(1L, "Smartphone 1", 500.0),
            createMockSmartphone(2L, "Smartphone 2", 800.0)
        );
        
        when(productRepository.findAll()).thenReturn(mockProducts);

        // Act - Este método debería activar el aspecto de tiempo de ejecución
        List<Product> result = productService.getAllProducts();

        // Assert
        assertNotNull(result, "Product list should not be null");
        assertEquals(2, result.size(), "Should return 2 products");

        // El aspecto @ExecutionTime debería haber medido el tiempo de ejecución
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testAopAnnotationsOnGetProductById() {
        // Arrange
        Long productId = 1L;
        Smartphone mockProduct = createMockSmartphone(productId, "Test Smartphone", 799.0);
        
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act - Este método debería activar aspectos de validación y tiempo
        Product result = productService.getProductById(productId);

        // Assert
        assertNotNull(result, "Product should be found");
        assertEquals(productId, result.getId(), "Product ID should match");

        // Los aspectos deberían haber sido ejecutados:
        // 1. @ExecutionTime - medición de tiempo
        // 2. @ValidateParameters - validación de ID no null
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testValidationAspectWithNullProduct() {
        // Act & Assert - Debería lanzar excepción por validación AOP
        assertThrows(IllegalArgumentException.class, () -> {
            productService.saveProduct(null);
        }, "Should throw IllegalArgumentException for null product");

        // No debería llamar al repository si la validación falla
        verify(productRepository, never()).save(any());
    }

    @Test
    void testValidationAspectWithNullId() {
        // Act & Assert - Debería lanzar excepción por validación AOP
        assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductById(null);
        }, "Should throw IllegalArgumentException for null ID");

        // No debería llamar al repository si la validación falla
        verify(productRepository, never()).findById(any());
    }

    @Test
    void testAopAnnotationsOnDeleteProduct() {
        // Arrange
        Long productId = 1L;
        
        doNothing().when(productRepository).deleteById(productId);

        // Act - Este método debería activar aspectos de auditoría, tiempo y validación
        assertDoesNotThrow(() -> {
            productService.deleteProduct(productId);
        });

        // Assert
        // Los aspectos AOP deberían haber sido ejecutados:
        // 1. @Auditable - auditoría de eliminación
        // 2. @ExecutionTime - medición de tiempo
        // 3. @ValidateParameters - validación de ID no null
        verify(productRepository, times(1)).deleteById(productId);
    }

    /**
     * Helper method para crear smartphones de prueba
     */
    private Smartphone createMockSmartphone(Long id, String name, Double price) {
        Smartphone smartphone = new Smartphone();
        smartphone.setId(id);
        smartphone.setName(name);
        smartphone.setPrice(price);
        smartphone.setCategory("Electronics");
        smartphone.setBrand("TestBrand");
        smartphone.setModel("TestModel");
        smartphone.setOperatingSystem("Android");
        smartphone.setStorageCapacity(128);
        smartphone.setRam(8);
        smartphone.setProcessor("TestProcessor");
        smartphone.setScreenSize(6.1);
        smartphone.setStock(10);
        return smartphone;
    }
}
