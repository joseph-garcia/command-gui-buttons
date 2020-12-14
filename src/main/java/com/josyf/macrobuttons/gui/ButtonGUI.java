package com.josyf.macrobuttons.gui;

import com.josyf.macrobuttons.ConfigFile;
import com.josyf.macrobuttons.MacroButtons;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.TranslatableText;

public class ButtonGUI extends LightweightGuiDescription {

    int xValue = 0;
    int yValue = 1;

    private static String ConfigSettings = ConfigFile.readFile();

    public ButtonGUI() {

        // initialize root panel of GUI
        WGridPanel root = new WGridPanel();

        setupBackground(root);

        // ######### DEBUG BUTTONS ############
        // example button to create config JSON
        WButton button = new WButton(new TranslatableText("Serialize"));
        button.setOnClick(() -> {
            MacroButtons.printMessage("serializing");
            ConfigFile.serializeCommand();
        });
        root.add(button, xValue, yValue + 9, 4, 1);

        // example load serialization button
        WButton button2 = new WButton(new TranslatableText("Load Serialization"));
        button2.setOnClick(() -> {
            MacroButtons.printMessage("load serialization");
            ConfigFile.loadSerialization();
        });
        root.add(button2, xValue + 4, yValue + 9, 6, 1);

        // read json file button
        WButton button3 = new WButton(new TranslatableText("Read command json"));
        button3.setOnClick(() -> {
            ConfigFile.readFile();
        });
        root.add(button3, xValue + 10, yValue + 9, 6, 1);
        // ######### DEBUG BUTTONS ############

        // Text GUI, not needed yet
        // WLabel label = new WLabel(new LiteralText("Test"), 0xFFFFFF);
        // root.add(label, 0, 4, 2, 1);

        addCommandSection(root);

        root.validate(this);
    }

    public static String getConfig() {
        return ConfigSettings;
    }

    private void addCommandSection(WGridPanel root) {
        // Add text field for command NAME entry
        WTextField nameTextField = new WTextField();
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
            addGUIButton(root, xValue, nameTextField, commandTextField);
        });
        root.add(addCmdBtn, 18, 12, 1, 1);
    }

    private void addGUIButton(WGridPanel root, int x, WTextField name, WTextField command) {
        // Only add the button if there are contents in both
        if (!name.getText().equals("") && !command.getText().equals("")) {

            System.out.println("Here, command is " + command.getText());
            String instancedString = command.getText();

            WButton button = new WButton(new TranslatableText(name.getText()));
            button.setOnClick(() -> {
                MacroButtons.printMessage(instancedString);
                System.out.println("Command was " + instancedString);
            });
            // int newX = incrementNumber(x, 4);
            System.out.println("x val: " + xValue);
            System.out.println("y val: " + yValue);

            root.add(button, xValue, yValue, 4, 1);

            adjustBounds();

            name.setText("");
            command.setText("");

            root.validate(this);

        } else {
            System.out.println("No name and value entered!");
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
