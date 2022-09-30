package model;


import org.json.JSONObject;
import persistence.ToJson;

//Represents an item with a name and quantity
public class Item implements ToJson {

    private String itemName;
    private int quantity;

    /*REQUIRES: itemName is not empty
        EFFECTS: name of item is set to itemName, if initialQuantity >=0,
        then quantity of item is set to initialQuantity, otherwise, it is set to 0
     */
    public Item(String itemName, int initialQuantity) {
        this.itemName = itemName;
        if (initialQuantity >= 0) {
            quantity = initialQuantity;
        } else {
            quantity = 0;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public String getItemName() {
        return itemName;
    }

    /*
    REQUIRES: amount >=0
    EFFECTS: add amount to quantity and return new quantity
     */
    public int increaseQuantity(int amount) {
        quantity = quantity + amount;
        return quantity;
    }

    /*
    REQUIRES: amount >= 0
    EFFECTS: if quantity - amount >= 0, remove amount from quantity, otherwise
                  set quantity to 0 then return new quantity
     */
    public int decreaseQuantity(int amount) {
        if (quantity - amount >= 0) {
            quantity = quantity - amount;
        } else {
            quantity = 0;
        }
        return quantity;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Item Name", itemName);
        json.put("Quantity", quantity);
        return json;
    }
    //  Code taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
}
