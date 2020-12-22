package com.josyf.macrobuttons.gui;

import com.josyf.macrobuttons.ConfigFile;
import com.josyf.macrobuttons.MacroButtons;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.TranslatableText;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ButtonGUI extends LightweightGuiDescription {

    int xValue = 0;
    int yValue = 1;

    public ButtonGUI() {

        // initialize root panel of GUI
        WGridPanel root = new WGridPanel();
        setupBackground(root);

        // Text GUI, not needed yet
        // WLabel label = new WLabel(new LiteralText("Test"), 0xFFFFFF);
        // root.add(label, 0, 4, 2, 1);

        addSavedButtons(root);
        addCommandSection(root);
        root.validate(this);
    }

    private void addCommandSection(WGridPanel root) {
        // Add text field for command NAME entry
        WTextField nameTextField = new WTextField();
        nameTextField.setMaxLength(10);
        nameTextField.setSuggestion("Name");
        root.add(nameTextField, 0, 12, 6, 1);

        // Add text field for command / entry
        WTextField commandTextField = new WTextField();
        commandTextField.setSuggestion("/command");
        commandTextField.setMaxLength(100);
        root.add(commandTextField, 6, 12, 11, 1);

        // Add button for command entry
        WButton addCmdBtn = new WButton(new TranslatableText("+"));
        addCmdBtn.setOnClick(() -> {
            addGUIButton(root, nameTextField, commandTextField);
        });
        root.add(addCmdBtn, 18, 12, 1, 1);
    }

    // Function to save newly added buttons to commands.json
    private void addGUIButton(WGridPanel root, WTextField name, WTextField command) {
        // Only add the button if there are contents in both
        if (!name.getText().equals("") && !command.getText().equals("")) {

            if (!isListTooLong()) {
                String commandString = command.getText();
                WButton button = new WButton(new TranslatableText(name.getText()));
                button.setOnClick(() -> {
                    MacroButtons.runCommand(commandString);
                });
                root.add(button, xValue, yValue, 4, 1);

                // Create a new Json object & append to masterCommList
                JSONObject newJsonObject = new JSONObject();
                newJsonObject.put("name", name.getText());
                newJsonObject.put("command", command.getText());

//                // append the buttons to masterList for future loading
                ConfigFile.addObjectToCommList(newJsonObject);
                ConfigFile.appendToFile(newJsonObject);

                adjustBounds();
            }

            name.setText("");
            command.setText("");

            root.validate(this);

        } else {
            System.out.println("No name and value entered!");
        }

    }

    // function to load buttons from commands.json
    private void addGUIButton(WGridPanel root, String name, String command) {
        if (!name.equals("") && !command.equals("")) {
            WButton button = new WButton(new TranslatableText(name));
            button.setOnClick(() -> {
                MacroButtons.runCommand(command);
            });
            root.add(button, xValue, yValue, 4, 1);
            adjustBounds();
            root.validate(this);
        } else {
            System.out.println("No name and value entered!");
        }
    }


    // Array will contain String class types. Convert these to objects.
    private void addSavedButtons(WGridPanel root) {
        ArrayList<JSONObject> commListCopy = MacroButtons.getMasterCommList();
        // Then convert the objects to buttons
        if (commListCopy != null) {
            for (int i = 0; i < commListCopy.size(); i++) {
                String name = commListCopy.get(i).get("name").toString();
                String command = commListCopy.get(i).get("command").toString();
                addGUIButton(root, name, command);
                if (i >= 19) break;
            }
        }

    }


    private void adjustBounds() {
        if (xValue % 12 == 0 && xValue != 0) {
            yValue += 2;
            xValue = 0;
        } else {
            xValue += 4;
        }
    }

    private boolean isListTooLong() {
        return MacroButtons.getMasterCommList().size() > 19;
    }

    // Change background panel color to transparent black
    @Override
    public void addPainters() {
        super.addPainters();
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(0x4D000000));
    }

    private void setupBackground(WGridPanel root) {
        setRootPanel(root);
        root.setSize(350, 240);
    }


}
