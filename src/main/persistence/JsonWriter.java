package persistence;

import model.Inventory;
import org.json.JSONObject;

import java.io.*;

//Writer that creates JSON representation of an inventory to a file
public class JsonWriter {
    private String fileDestination;
    private PrintWriter writer;

    //MODIFIES: this
    //EFFECTS: constructs a JsonWriter to write file in fileDestination
    public JsonWriter(String fileDestination) {
        this.fileDestination = fileDestination;
    }

    //MODIFIES: this
    //EFFECTS: opens writer, throws FileNotFoundException if file destination cannot be written in
    public void openWriter() throws FileNotFoundException {
        writer = new PrintWriter(fileDestination);
    }

    //MODIFIES: this
    //EFFECTS: closes the writer
    public void closeWriter() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes a string to JSON file
    public void saveToFile(String string) {
        writer.print(string);
    }

    //MODIFIES: this
    //EFFECTS: converts inventory to JSON and adds it to file
    public void write(Inventory inventory) {
        JSONObject json = inventory.toJson();
        saveToFile(json.toString(4));
    }
    // Code used from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
}
