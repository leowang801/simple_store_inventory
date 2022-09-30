package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    private Item testItem1;
    private Item testItem2;
    private Item testItem3;

    @BeforeEach
    void runBefore() {
        testItem1 = new Item("Apples", 20);
        testItem2 = new Item("Pears", 10);
    }

    @Test
    void testConstructor() {
        assertEquals(20, testItem1.getQuantity());
        assertEquals("Apples", testItem1.getItemName());

        assertEquals(10, testItem2.getQuantity());
        assertEquals("Pears", testItem2.getItemName());

        testItem3 = new Item("Peaches", -5);
        assertEquals(0,testItem3.getQuantity());
        assertEquals("Peaches", testItem3.getItemName());
    }

    @Test
    void testIncreaseQuantity() {
        assertEquals(30, testItem1.increaseQuantity(10));
        assertEquals(35, testItem1.increaseQuantity(5));

        assertEquals(40, testItem2.increaseQuantity(30));
        assertEquals(75, testItem2.increaseQuantity(35));
    }

    @Test
    void testDecreaseQuantity() {
        assertEquals(8, testItem1.decreaseQuantity(12));
        assertEquals(0, testItem1.decreaseQuantity(10));

        assertEquals(6, testItem2.decreaseQuantity(4));
        assertEquals(0, testItem2.decreaseQuantity(6));
        assertEquals(0, testItem2.decreaseQuantity(1));

    }
}