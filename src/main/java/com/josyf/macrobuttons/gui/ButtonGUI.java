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

        // example button to create config JSON
        WButton button = new WButton(new TranslatableText("Serialize"));
        button.setOnClick(() -> {
            MacroButtons.printMessage();
            ConfigFile.serializeCommand();
        });
        root.add(button, xValue, yValue, 4, 1);

        // example load serialization button
        WButton button2 = new WButton(new TranslatableText("Load Serialization"));
        button2.setOnClick(() -> {
            MacroButtons.printMessage();
            ConfigFile.loadSerialization();
        });
        root.add(button2, xValue + 4, yValue, 6, 1);

        // read json file button
        WButton button3 = new WButton(new TranslatableText("Read command json"));
        button3.setOnClick(() -> {
            ConfigFile.readFile();
        });
        root.add(button3, xValue + 10, yValue, 6, 1);

        // Text GUI, not needed yet
        // WLabel label = new WLabel(new LiteralText("Test"), 0xFFFFFF);
        // root.add(label, 0, 4, 2, 1);

        addCommandSection(root);

        root.validate(this);
    }

    public static void setConfig(String configSettings) {
        ConfigSettings = configSettings;
    }

    public static String getConfig() {
        return ConfigSettings;
    }

    private void addCommandSection(WGridPanel root) {
        // Add text field for command entry
        WTextField textField = new WTextField();
        root.add(textField, 6, 12, 6, 1);

        // Add button for command entry
        WButton addCmdBtn = new WButton(new TranslatableText("+"));
        addCmdBtn.setOnClick(() -> {
            addGUIButton(root, xValue);
        });
        root.add(addCmdBtn, 13, 12, 1, 1);
    }

    private void addGUIButton(WGridPanel root, int x) {
        WButton button = new WButton(new TranslatableText("Button"));
        button.setOnClick(() -> {
            // MacroButtons.printMessage();
        });
        // int newX = incrementNumber(x, 4);
        System.out.println("x val: " + xValue);
        System.out.println("y val: " + yValue);

        root.add(button, xValue, yValue, 4, 1);

        adjustBounds();

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
        root.setSize(300, 240);
    }


}
