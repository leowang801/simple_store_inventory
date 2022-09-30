package persistence;

import model.Inventory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {

    @Test
    void testTryReadNonExistentFile() {
        JsonReader testReader = new JsonReader("./data/fakeFile.json");
        try {
            Inventory testInventory = testReader.read();
            fail("IOException was expected");
        } catch (IOException e) {
            // correct response
        }
    }

    @Test
    void testReadEmptyInventory() {
        JsonReader testReader = new JsonReader("./data/testReadEmptyInventory.json");
        try {
            Inventory testInventory = testReader.read();
            assertEquals("Empty Inventory", testInventory.getInventoryName());
            assertEquals(0, testInventory.length());
        } catch (IOException e) {
            fail("Couldn't read from file is available");
        }
    }

    @Test
    void readNormalInventory() {
        JsonReader testReader = new JsonReader("./data/testReadNormalInventory.json");
        try {
            Inventory testInventory = testReader.read();
            assertEquals("Normal Inventory", testInventory.getInventoryName());
            assertEquals(3, testInventory.length());

            checkItem("Apples", 10, testInventory.getItem("Apples"));
            checkItem("Apples", 10, testInventory.getItemFromIndex(0));

            checkItem("Oranges", 20, testInventory.getItem("Oranges"));
            checkItem("Oranges", 20, testInventory.getItemFromIndex(1));

            checkItem("Bananas", 30, testInventory.getItem("Bananas"));
            checkItem("Bananas", 30, testInventory.getItemFromIndex(2));
        } catch (IOException e) {
            fail("Couldn't read from file is available");
        }
    }
    // Tests used from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
}
