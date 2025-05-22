package inventory.service;

import inventory.errors.ValidationException;
import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class InventoryServiceTest {

    private static InventoryService service;

    private final String name="piesa";
    private final double goodPrice = 1.0;
    private final int goodInStock = 7;
    private final int min =5;
    private final int max = 10;
    private final int machineId=1;

    @BeforeAll
    static void setUpAll() {
        InventoryRepository repo = new InventoryRepository();
        service = new InventoryService(repo);
    }

    @ParameterizedTest
    @MethodSource("doubleIntValidProvider")
    @DisplayName("Add valid part")
    @Tag("valid")
    @Order(1)
    void addValidInHousePart(double price, int inStock) {
        //assertNull(service.lookupPart(name));
        service.addInhousePart(name, price, inStock, min, max, machineId);
        assertNotNull(service.lookupPart(name));
    }

    static Stream<Arguments> doubleIntValidProvider() {
        return Stream.of(
                Arguments.of(0.1, 5),
                Arguments.of(1.0, 6),
                Arguments.of(Double.MAX_VALUE-1.0, 9),
                Arguments.of(Double.MAX_VALUE, 10)
        );
    }


    @ParameterizedTest
    @ValueSource(doubles = {-1.0})
    @DisplayName("Add invalid price part")
    @Tag("price")
    @Order(2)
    void addInvalidPriceInHousePart(double price) {
        assertThrows(ValidationException.class, () -> service.addInhousePart(name, price, goodInStock, min, max, machineId));
        try {
            service.addInhousePart(name, price, goodInStock, min, max, machineId);
            fail();
        } catch (ValidationException e) {
            assertEquals("The price must be greater than 0.0", e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {min-1, max+1})
    @DisplayName("Add invalid inStock part")
    @Tag("inStock")
    @Order(3)
    void addInvalidInStockInHousePart(int inStock) {
        assertThrows(ValidationException.class, () -> service.addInhousePart(name, goodPrice, inStock, min, max, machineId));
        try {
            service.addInhousePart(name, goodPrice, inStock, min, max, machineId);
            fail();
        } catch (ValidationException e) {
            switch (inStock) {
                case min - 1 -> assertEquals("Inventory level is lower than minimum value. ", e.getMessage());
                case max + 1 -> assertEquals("Inventory level is higher than the maximum value. ", e.getMessage());
            }
        }
    }


    @Test
    @Disabled("In stock is overflowing")
    void addInvalidInStockInHousePart() {
        int inStock = max+1;
        assertThrows(ValidationException.class, () -> service.addInhousePart(name, 10, inStock, min, max, machineId));
    }


    @AfterEach
    void tearDown() {
        service.deleteProduct(service.lookupProduct(name));
    }
}