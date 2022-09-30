package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.ToJson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Inventory implements ToJson {

    private List<Item> inventory;
    private String inventoryName;

    /*
    EFFECTS: creates a new empty inventory with a name
     */
    public Inventory(String inventoryName) {
        inventory = new ArrayList<>();
        this.inventoryName = inventoryName;
    }

    /*
    EFFECTS: search inventory for item, return true if found, otherwise return false
     */
    public boolean searchInventory(String itemName) {
        for (Item item : inventory) {
            if (Objects.equals(item.getItemName(), itemName)) {
                return true;
            }
        }
        return false;
    }

    /*
    REQUIRES: inventory contains the item we are looking for
    EFFECTS: searches inventory for item with given name and returns the item if found,
                   otherwise return null
     */
    public Item getItem(String itemName) {
        for (Item item : inventory) {
            if (Objects.equals(item.getItemName(), itemName)) {
                return item;
            }
        }
        return null;
    }

    /*
    MODIFIES: this
    EFFECTS: if there is already an item that exists in the inventory, add quantity of item to existing item,
                  if item is new, add to back of inventory
     */
    public void addItem(Item item) {
        if (searchInventory(item.getItemName())) {
            getItem(item.getItemName()).increaseQuantity(item.getQuantity());
        } else {
            inventory.add(item);
        }
    }

    /*
    REQUIRES: item is in inventory and amount >=0
    MODIFIES: this
    EFFECTS: if amount removed is greater than or equal to the item's quantity, remove
                   item from inventory, otherwise, decrease item quantity by amount
     */
    public void removeItem(Item item, int amount) {
        if (searchInventory(item.getItemName())) {
            if (amount >= item.getQuantity()) {
                inventory.remove(getItemIndex(item));
            } else {
                getItem((item.getItemName())).decreaseQuantity(amount);
            }
        }
    }

    /*
    EFFECTS: return length of inventory
     */
    public int length() {
        return inventory.size();
    }

    /*
    EFFECTS: return true if inventory is empty
     */
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    public int getItemIndex(Item item) {
        return inventory.indexOf(item);
    }

    /*
    EFFECTS: return item based on position in inventory with 0 based indexing
     */
    public Item getItemFromIndex(int index) {
        return inventory.get(index);
    }

    /*
    EFFECTS: returns name of every item in inventory
     */
    public List<String> entireInventory() {
        ArrayList<String> allNames = new ArrayList<>();

        for (Item item : inventory) {
            allNames.add(item.getItemName());
        }
        return allNames;
    }

    public String getInventoryName() {
        return inventoryName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Inventory", inventoryToJson());
        json.put("Inventory Name", inventoryName);
        return json;
    }

    public List getInventory() {
        return inventory;
    }

    //EFFECTS: returns items in this inventory as JSON array
    private JSONArray inventoryToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Item item : inventory) {
            jsonArray.put(item.toJson());
        }

        return jsonArray;
    }
    //  Code taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
}
