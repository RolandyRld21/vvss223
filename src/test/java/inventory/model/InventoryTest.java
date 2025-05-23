package inventory.model;

import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Inventory inventory;
    private InventoryRepository repo;
    private Product product;
    private ObservableList<Part> partList = FXCollections.observableArrayList();;

    @BeforeEach
    void setUp() {
        try{
            repo = new InventoryRepository();
            inventory = repo.getInventory();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Empty search item")
    void lookUpProduct_TC01() {
        assert inventory.lookupProduct("") == null;
    }

    @Test
    @DisplayName("Search item found")
    void lookUpProduct_TC02() {
        Assertions.assertEquals(inventory.lookupProduct("Test Product").getProductId(), 1);
    }

    @Test
    @DisplayName("Search item not found")
    void lookUpProduct_TC03() {
        assertNull(inventory.lookupProduct("TEST"));
    }

    @Test
    @DisplayName("Empty list")
    void lookUpProduct_TC04() {
        // Șterge toate produsele (dacă există)
        for (Product p : new ArrayList<>(inventory.getProducts())) {
            inventory.removeProduct(p);
        }

        // Acum lista ar trebui să fie goală
        List<Product> prods = inventory.getProducts();
        assertTrue(prods.isEmpty(), "Lista de produse ar trebui să fie goală!");

        // Dacă vrei să verifici și lookupProduct, că nu găsește nimic:
        assertNull(inventory.lookupProduct("TEST"), "lookupProduct ar trebui să returneze null dacă nu găsește nimic");
    }


}
