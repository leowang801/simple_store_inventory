package persistence;

import model.Inventory;
import model.Item;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {
    @Test
    void testInvalidFile() {
        try {
            Inventory testInventory = new Inventory("Test Inventory");
            JsonWriter testWriter = new JsonWriter("./data/my\0illegal:fileName.json");
            testWriter.openWriter();
            fail("IOException was expected");
        } catch (IOException e) {
            // correct
        }
    }

    @Test
    void testWriterEmptyInventory() {
        try {
            Inventory testInventory = new Inventory("Empty Inventory");
            JsonWriter testWriter = new JsonWriter("./data/testWriteEmptyInventory.json");
            testWriter.openWriter();
            testWriter.write(testInventory);
            testWriter.closeWriter();

            JsonReader testReader = new JsonReader("./data/testWriteEmptyInventory.json");
            testInventory = testReader.read();
            assertEquals("Empty Inventory", testInventory.getInventoryName());
            assertEquals(0, testInventory.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterNormalInventory() {
        try {
            Inventory testInventory = new Inventory("Normal Inventory");
            testInventory.addItem(new Item("Pears", 5));
            testInventory.addItem(new Item("Kiwi", 6));
            testInventory.addItem(new Item("Strawberry", 7));
            JsonWriter testWriter = new JsonWriter("./data/testWriteNormalInventory.json");
            testWriter.openWriter();
            testWriter.write(testInventory);
            testWriter.closeWriter();

            JsonReader testReader = new JsonReader("./data/testWriteNormalInventory.json");
            testInventory = testReader.read();
            assertEquals("Normal Inventory", testInventory.getInventoryName());
            assertEquals(3, testInventory.length());
            checkItem("Pears", 5, testInventory.getItem("Pears"));
            checkItem("Pears", 5, testInventory.getItemFromIndex(0));
            checkItem("Kiwi", 6, testInventory.getItem("Kiwi"));
            checkItem("Kiwi", 6, testInventory.getItemFromIndex(1));
            checkItem("Strawberry", 7, testInventory.getItem("Strawberry"));
            checkItem("Strawberry", 7, testInventory.getItemFromIndex(2));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    // Tests used from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
}
