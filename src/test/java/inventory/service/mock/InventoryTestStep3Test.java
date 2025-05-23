package inventory.service.mock;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InventoryTestStep3Test {
    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        // Inițializăm un InventoryRepository real
        inventoryRepository = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepository);
    }

    @Test
    public void testAddProduct() {
        int old_size_r = inventoryRepository.getAllProducts().size();
        int old_size_s = inventoryService.getAllProducts().size();
        // Create mock data
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(new InhousePart(1, "Test Part", 5.0, 10, 1, 20, 50));

        // Call service method
        inventoryService.addProduct("Test Product", 50.0, 20, 5, 30, parts);

        assertEquals(old_size_r + 1, inventoryRepository.getAllProducts().size());
        assertEquals(old_size_s + 1, inventoryService.getAllProducts().size());
    }

    @Test
    public void deleteProduct() {
        int old_size_r = inventoryRepository.getAllProducts().size();
        int old_size_s = inventoryService.getAllProducts().size();
        // Create mock data
        ObservableList<Part> parts = FXCollections.observableArrayList();
        parts.add(new InhousePart(1, "Test Part", 5.0, 10, 1, 20, 50));

        // Call service method
        inventoryService.addProduct("Test Product", 50.0, 20, 5, 30, parts);

        assertEquals(old_size_r + 1, inventoryRepository.getAllProducts().size());
        assertEquals(old_size_s + 1, inventoryService.getAllProducts().size());

        inventoryRepository.deleteProduct(inventoryService.getAllProducts().get(inventoryService.getAllProducts().size() - 1));
        assertEquals(old_size_r, inventoryRepository.getAllProducts().size());
        assertEquals(old_size_s, inventoryService.getAllProducts().size());
    }
}
