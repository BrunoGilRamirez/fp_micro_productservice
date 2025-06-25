package com.aspiresys.fp_micro_productservice.product;

import com.aspiresys.fp_micro_productservice.common.dto.AppResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;




/**
 * Unit tests for the {@link ProductController} class.
 * <p>
 * This test class verifies the behavior of the {@code getBodyExamples} method,
 * ensuring that it returns a {@link ResponseEntity} containing an {@link AppResponse}
 * with example product data for different categories such as clothes, electronics, and smartphone.
 * <p>
 * The test checks:
 * <ul>
 *   <li>The response is not null and has a 200 status code.</li>
 *   <li>The response body contains the expected success message.</li>
 *   <li>The example data map contains entries for "clothes", "electronics", and "smartphone".</li>
 *   <li>Each example entry contains the expected fields and values for its category.</li>
 * </ul>
 */
public class ProductControllerTest {

    @Test
    public void testGetBodyExamplesReturnsExpectedExamples() {
        ProductController controller = new ProductController();

        ResponseEntity<AppResponse<Map<String, Object>>> response = controller.getBodyExamples();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());

        AppResponse<Map<String, Object>> appResponse = response.getBody();
        assertNotNull(appResponse);
        assertEquals("Body examples retrieved successfully", appResponse.getMessage());

        Map<String, Object> examples = appResponse.getData();
        assertNotNull(examples);

        // Check clothes example
        assertTrue(examples.containsKey("clothes"));
        @SuppressWarnings("unchecked")
        Map<String, Object> clothes = (Map<String, Object>) examples.get("clothes");
        assertEquals("Remera", clothes.get("name"));
        assertEquals(1000.0, (Double) clothes.get("price"), 0.001);
        assertEquals("clothes", clothes.get("category"));
        assertEquals("url", clothes.get("imageUrl"));
        assertEquals(10, clothes.get("stock"));
        assertEquals("M", clothes.get("size"));
        assertEquals("Azul", clothes.get("color"));
        assertEquals("Algodón", clothes.get("fabricType"));

        // Check electronics example
        assertTrue(examples.containsKey("electronics"));
        @SuppressWarnings("unchecked")
        Map<String, Object> electronics = (Map<String, Object>) examples.get("electronics");
        assertEquals("Televisor", electronics.get("name"));
        assertEquals(50000.0, (Double) electronics.get("price"), 0.001);
        assertEquals("electronics", electronics.get("category"));
        assertEquals("url", electronics.get("imageUrl"));
        assertEquals(5, electronics.get("stock"));
        assertEquals("Samsung", electronics.get("brand"));
        assertEquals("QLED", electronics.get("model"));
        assertEquals("2 años", electronics.get("warrantyPeriod"));
        assertEquals("4K UHD", electronics.get("specifications"));

        // Check smartphone example
        assertTrue(examples.containsKey("smartphone"));
        @SuppressWarnings("unchecked")
        Map<String, Object> smartphone = (Map<String, Object>) examples.get("smartphone");
        assertEquals("iPhone 15", smartphone.get("name"));
        assertEquals(120000.0, (Double) smartphone.get("price"), 0.001);
        assertEquals("smartphone", smartphone.get("category"));
        assertEquals("url", smartphone.get("imageUrl"));
        assertEquals(3, smartphone.get("stock"));
        assertEquals("Apple", smartphone.get("brand"));
        assertEquals("15 Pro", smartphone.get("model"));
        assertEquals("1 año", smartphone.get("warrantyPeriod"));
        assertEquals("128GB", smartphone.get("specifications"));
        assertEquals("iOS", smartphone.get("operatingSystem"));
        assertEquals(128, smartphone.get("storageCapacity"));
        assertEquals(6, smartphone.get("ram"));
        assertEquals("A17", smartphone.get("processor"));
        assertEquals(6.1, (Double) smartphone.get("screenSize"), 0.001);
    }
}