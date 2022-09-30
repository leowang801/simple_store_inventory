package persistence;

import model.Item;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkItem(String itemName, int quantity, Item item) {
        assertEquals(itemName, item.getItemName());
        assertEquals(quantity, item.getQuantity());
    }
    // Tests used from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
}
