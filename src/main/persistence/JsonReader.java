package persistence;

import model.Inventory;
import model.Item;

import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// A reader that reads inventory from JSON data stored in a file
public class JsonReader {
    private String file;

    //EFFECTS: constructs a reader to read from the given file
    public JsonReader(String file) {
        this.file = file;
    }

    //EFFECTS: reads given file as a string and returns it
    private String readFile(String file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(file), StandardCharsets.UTF_8)) {
            stream.forEach(x -> stringBuilder.append(x));
        }

        return stringBuilder.toString();
    }

    //EFFECTS: parses inventory from JSON then returns it
    private Inventory parsedInventory(JSONObject jsonObject) {
        String inventoryName = jsonObject.getString("Inventory Name");
        Inventory inventory = new Inventory(inventoryName);
        addMultipleItems(inventory, jsonObject);
        return inventory;
    }

    //EFFECTS: reads inventory from file and returns it and throws IOException if an error occurs
    public Inventory read() throws IOException {
        String fileContents = readFile(file);
        JSONObject json = new JSONObject(fileContents);
        return parsedInventory(json);
    }

    //MODIFIES: inventory
    //EFFECTS: parses multiple items from JSON object and adds them to inventory
    private void addMultipleItems(Inventory inventory, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Inventory");
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addOneItem(nextItem, inventory);
        }
    }

    //MODIFIES: inventory
    //EFFECTS: parses item from JSON object and adds it to inventory
    private void addOneItem(JSONObject jsonObject, Inventory inventory) {
        String itemName = jsonObject.getString("Item Name");
        int quantity = jsonObject.getInt("Quantity");
        Item item = new Item(itemName, quantity);
        inventory.addItem(item);
    }
    // Code used from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
}
