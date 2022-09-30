package ui;

import model.Inventory;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

// Store Inventory Application
public class InventoryApp {

    private static final String JSON_FILE = "./data/inventory.json";

    private Inventory inventory;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    //EFFECTS: initializes inventory and scanner and runs inventory application
    public InventoryApp() {
        jsonWriter = new JsonWriter(JSON_FILE);
        jsonReader = new JsonReader(JSON_FILE);
        runInventory();
    }

    /*
    MODIFIES: this
    EFFECTS: processes items inputted by the user
    Code based on TellerApp class from example provided on edX
     */
    private void runInventory() {
        boolean keepRunningProgram = true;
        String inputKey;

        startProgram();

        while (keepRunningProgram) {
            menu();
            inputKey = input.next();
            inputKey = inputKey.toLowerCase();

            if (inputKey.equals("q")) {
                keepRunningProgram = false;
            } else {
                processInputs(inputKey);
            }
        }
        System.out.println("Program Shut Down. Goodbye!");
    }

    /*
    MODIFIES: this
    EFFECTS: initiates inventory and scanner
     */
    private void startProgram() {
        inventory = new Inventory("Store Inventory");
        input = new Scanner(System.in);
    }

    //EFFECTS: displays options to user
    private void menu() {
        System.out.println("Press i to check inventory");
        System.out.println("Press e to edit inventory");
        System.out.println("Press f to search for item");
        System.out.println("Press s to save inventory");
        System.out.println("Press l to load inventory from file");
        System.out.println("Press q to exit");
    }

    //MODIFIES: this
    //EFFECTS: processes user inputs for the program
    private void processInputs(String inputKey) {
        if (Objects.equals(inputKey, "i")) {
            checkInventory();
        } else if (Objects.equals(inputKey, "e")) {
            editInventory();
        } else if (Objects.equals(inputKey, "f")) {
            itemInfo();
        } else if (Objects.equals(inputKey, "s")) {
            saveInventory();
        } else if (Objects.equals(inputKey, "l")) {
            loadInventory();
        } else {
            System.out.println("Invalid selection, "
                    + "please select from the options above by pressing the appropriate key");
        }
    }

    //MODIFIES: this
    //EFFECTS: processes user input to add or remove items from inventory
    private void editInventory() {
        System.out.println("Press a to add item to inventory");
        System.out.println("Press r to remove item from inventory");

        String key = input.next();

        if ((Objects.equals(key, "a"))) {
            addToInventory();
        } else if ((Objects.equals(key, "r"))) {
            removeFromInventory();
        }
    }

    //EFFECTS: adds item to inventory
    private void addToInventory() {
        inventory.addItem(createItem());
    }

    //REQUIRES: item name is not empty, and quantity >=0
    //EFFECTS: creates item to be added
    private Item createItem() {
        String name;
        int quantity;
        Item item;

        System.out.print("Enter Item Name: ");
        name = input.next();

        System.out.print("Enter Quantity of Item to add: ");
        quantity = input.nextInt();

        item = new Item(name, quantity);

        return item;
    }

    //REQUIRES: item is in inventory and quantity >= 0
    //MODIFIES: this
    //EFFECTS: removes quantity of item from inventory
    private void removeFromInventory() {
        Item item;
        int quantity;

        System.out.print("Enter Item Name: ");
        item = inventory.getItem(input.next());

        System.out.print("Enter Quantity to Remove: ");
        quantity = input.nextInt();

        inventory.removeItem(inventory.getItem(item.getItemName()), quantity);
    }

    //EFFECTS: prints names of every item in inventory
    private void checkInventory() {
        System.out.println(inventory.entireInventory());
    }

    //MODIFIES: this
    //EFFECTS: searches for item in inventory and prints quantity in stock if found,
    //              otherwise, print message saying the item is not in stock
    private void itemInfo() {
        String itemName;
        Item item;
        int itemQuantity;

        System.out.print("Enter Item Name to Search: ");
        itemName = input.next();

        if (inventory.searchInventory(itemName)) {
            item = inventory.getItem(itemName);
            itemQuantity = item.getQuantity();

            System.out.println(itemName + " is in stock | Quantity: " + itemQuantity);
        } else {
            System.out.println(itemName + " is NOT in stock");
        }
    }

    //MODIFIES: this
    //EFFECTS: saves inventory to file
    private void saveInventory() {
        try {
            jsonWriter.openWriter();
            jsonWriter.write(inventory);
            jsonWriter.closeWriter();
            System.out.println("Saved " + inventory.getInventoryName() + " to " + JSON_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot write in file: " + JSON_FILE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads inventory from file
    private void loadInventory() {
        try {
            inventory = jsonReader.read();
            System.out.println("Loaded " + inventory.getInventoryName() + " from " + JSON_FILE);
        } catch (IOException e) {
            System.out.println("Cannot read file: " + JSON_FILE);
        }
    }
    // Code used from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
}
