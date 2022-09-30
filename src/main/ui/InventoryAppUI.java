package ui;


import model.Inventory;
import model.Item;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InventoryAppUI extends JFrame {

    private static final String JSON_FILE = "./data/inventory.json";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Inventory inventory;
    private JFrame frame;
    private DefaultListModel itemNames;
    private DefaultListModel itemQuantities;


    //EFFECTS: initiates UI for inventory app
    public InventoryAppUI() {
        jsonWriter = new JsonWriter(JSON_FILE);
        jsonReader = new JsonReader(JSON_FILE);
        inventory = new Inventory("My Inventory");
        frame = new JFrame();

        addMenuButtons();

        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to frame
    public void addMenuButtons() {
        JPanel menuButtons = new JPanel();
        menuButtons.setLayout(new GridLayout(3, 3));
        menuButtons.add(new JButton(new CheckInventoryAction()));
        menuButtons.add(new JButton(new AddToInventoryAction()));
        menuButtons.add(new JButton(new RemoveFromInventoryAction()));
        menuButtons.add(new JButton(new SearchInventoryAction()));
        menuButtons.add(new JButton(new SaveInventoryAction()));
        menuButtons.add(new JButton(new LoadInventoryAction()));
        menuButtons.add(new JButton(new QuitAction()));

        frame.add(menuButtons);
    }

    // functionality of check inventory button
    private class CheckInventoryAction extends AbstractAction {
        CheckInventoryAction() {
            super("Check Inventory");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame inventoryDisplay = new JFrame();
            JPanel panel = new JPanel();
            placeItemsInList();

            JList names = new JList(itemNames);
            JList quantities = new JList(itemQuantities);

            panel.add(names);
            panel.add(quantities);

            inventoryDisplay.add(panel);
            inventoryDisplay.setSize(150, 300);
            inventoryDisplay.show();
            inventoryDisplay.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates lists of item names and item quantities
    private void placeItemsInList() {
        itemNames = new DefaultListModel<String>();
        itemQuantities = new DefaultListModel<String>();

        for (int i = 0; i < inventory.length(); i++) {
            String str = inventory.getItemFromIndex(i).getItemName();
            int quant = inventory.getItemFromIndex(i).getQuantity();
            itemNames.addElement(str);
            itemQuantities.addElement(quant);
        }
    }

    // functionality of add to inventory button
    private class AddToInventoryAction extends AbstractAction {
        AddToInventoryAction() {
            super("Add To Inventory");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            inventory.addItem(createItem());
        }
    }

    // functionality of remove from inventory button
    private class RemoveFromInventoryAction extends AbstractAction {
        RemoveFromInventoryAction() {
            super("Remove From Inventory");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String itemName = JOptionPane.showInputDialog(frame, "Enter Item Name");

            Item item = inventory.getItem(itemName);

            int quantity = Integer.parseInt(JOptionPane.showInputDialog(frame,
                    "Enter Item Quantity You Wish To Remove"));

            inventory.removeItem(inventory.getItem(item.getItemName()), quantity);
        }
    }

    //REQUIRES: item name is not empty, and quantity >=0
    //EFFECTS: creates item to be added
    private Item createItem() {
        Item item;
        String name = JOptionPane.showInputDialog(frame, "Enter Name");

        int quantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Item Quantity"));

        item = new Item(name, quantity);

        return item;
    }

    // functionality of search inventory button
    private class SearchInventoryAction extends AbstractAction {
        SearchInventoryAction() {
            super("Search Inventory");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String itemName = JOptionPane.showInputDialog(frame, "Enter Name Of Item You Wish To Search For");
            Item item;
            int itemQuantity;

            if (inventory.searchInventory(itemName)) {
                item = inventory.getItem(itemName);
                itemQuantity = item.getQuantity();

                JOptionPane.showMessageDialog(frame,
                        itemName + " Are In Stock | Quantity: " + itemQuantity,
                        "Alert",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        itemName + " Are NOT In Stock",
                        "Alert",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // functionality of save inventory button
    private class SaveInventoryAction extends AbstractAction {
        SaveInventoryAction() {
            super("Save Inventory");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.openWriter();
                jsonWriter.write(inventory);
                jsonWriter.closeWriter();
                JOptionPane.showMessageDialog(frame,
                        "Inventory Successfully Saved.", "Alert",
                        JOptionPane.WARNING_MESSAGE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // functionality of load inventory button
    private class LoadInventoryAction extends AbstractAction {
        LoadInventoryAction() {
            super("Load Inventory");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                inventory = jsonReader.read();
                JOptionPane.showMessageDialog(frame,
                        "Inventory Successfully Loaded From " + JSON_FILE, "Alert",
                        JOptionPane.WARNING_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // functionality of quit button
    private class QuitAction extends AbstractAction {
        QuitAction() {
            super("Quit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

}
