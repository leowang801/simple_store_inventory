package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    private Inventory testInventory1;
    private Inventory testInventory2;
    private Item testItem1;
    private Item testItem2;
    private Item testItem3;
    private Item testItem4;


    @BeforeEach
    void runBefore() {
        testItem1 = new Item("Apples", 20);
        testItem2 = new Item("Oranges", 30);
        testItem3 = new Item("Bananas", 40);
        testItem4 = new Item("Pears", 50);

        testInventory1 = new Inventory("TestInventory1");
        testInventory2 = new Inventory("TestInventory2");

        testInventory2.addItem(testItem1);
        testInventory2.addItem(testItem2);
        testInventory2.addItem(testItem3);
    }

    @Test
    void testConstructor() {
        assertTrue(testInventory1.isEmpty());
        assertEquals(3, testInventory2.length());

        assertEquals("TestInventory1", testInventory1.getInventoryName());
        assertEquals("TestInventory2", testInventory2.getInventoryName());
    }

    @Test
    void testSearchInventory() {
        assertFalse(testInventory1.searchInventory("Apples"));

        assertTrue(testInventory2.searchInventory("Apples"));
        assertTrue(testInventory2.searchInventory("Oranges"));
        assertTrue(testInventory2.searchInventory("Bananas"));
        assertFalse(testInventory2.searchInventory("Pears"));
    }

    @Test
    void testGetItem() {
        assertNull(testInventory2.getItem("Pears"));
        assertEquals(testItem1, testInventory2.getItem("Apples"));
        assertEquals(testItem2, testInventory2.getItem("Oranges"));
        assertEquals(testItem3, testInventory2.getItem("Bananas"));
    }

    @Test
    void testAddItemNotAlreadyInInventory() {
        testInventory1.addItem(testItem1);
        assertEquals(testItem1, testInventory1.getItem(testItem1.getItemName()));
        assertEquals(1, testInventory1.length());
        assertEquals(testItem1, testInventory1.getItemFromIndex(0));

        testInventory1.addItem(testItem2);
        assertEquals(testItem2, testInventory1.getItem(testItem2.getItemName()));
        assertEquals(2, testInventory1.length());
        assertEquals(testItem2, testInventory1.getItemFromIndex(1));

        testInventory2.addItem(testItem4);
        assertEquals(testItem4, testInventory2.getItemFromIndex(3));
        assertEquals(4, testInventory2.length());
    }

    @Test
    void testAddItemAlreadyInInventory() {
        testInventory2.addItem(testItem1);
        assertEquals(3, testInventory2.length());
        assertEquals(40, testItem1.getQuantity());

        testInventory2.addItem(testItem2);
        assertEquals(3, testInventory2.length());
        assertEquals(60, testItem2.getQuantity());

        testInventory2.addItem(testItem3);
        assertEquals(3, testInventory2.length());
        assertEquals(80, testItem3.getQuantity());
    }

    @Test
    void testRemoveItem() {
        testInventory2.removeItem(testItem1, 5);
        assertEquals(15, testItem1.getQuantity());

        testInventory2.removeItem(testItem1, 10);
        assertEquals(5, testItem1.getQuantity());

        testInventory2.removeItem(testItem2, 30);
        assertEquals(2, testInventory2.length());
        assertFalse(testInventory2.searchInventory("Oranges"));

        testInventory2.removeItem(testItem3, 100);
        assertEquals(1, testInventory2.length());
        assertFalse(testInventory2.searchInventory("Bananas"));

    }

    @Test
    void testGetItemIndex() {
        assertEquals(0, testInventory2.getItemIndex(testItem1));
        assertEquals(1, testInventory2.getItemIndex(testItem2));
        assertEquals(2, testInventory2.getItemIndex(testItem3));
    }

    @Test
    void testEntireInventory() {
        assertTrue(testInventory1.entireInventory().isEmpty());

        ArrayList<String> testListOfNamesOfItems = new ArrayList<>();
        testListOfNamesOfItems.add("Apples");
        testListOfNamesOfItems.add("Oranges");
        testListOfNamesOfItems.add("Bananas");

        assertEquals(testListOfNamesOfItems, testInventory2.entireInventory());
    }
}
