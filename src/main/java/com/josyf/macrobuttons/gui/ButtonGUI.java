package com.josyf.macrobuttons.gui;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.josyf.macrobuttons.MacroButtons;
import com.josyf.macrobuttons.Message;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.io.FileWriter;
import java.io.IOException;

public class ButtonGUI extends LightweightGuiDescription {

    int xValue = 0;
    int yValue = 1;

    private static FileWriter file;

    String JSONMessage = "not yet set";

    

    public ButtonGUI() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(300, 240);

        // WSprite icon = new WSprite(new Identifier("minecraft:textures/item/redstone.png"));
        // root.add(icon, 0, 2, 1, 1);

        // example button to play with
        WButton button = new WButton(new TranslatableText("Serialize"));
        button.setOnClick(() -> {
            MacroButtons.printMessage();
            createMessageForSerialization();
        });
        root.add(button, xValue, yValue, 4, 1);

        // example load serialization button
        WButton button2 = new WButton(new TranslatableText("Load Serialization"));
        button2.setOnClick(() -> {
            MacroButtons.printMessage();
            loadSerialization();
        });
        root.add(button2, xValue + 2, yValue, 4, 1);

        WLabel label = new WLabel(new LiteralText("Test"), 0xFFFFFF);
        root.add(label, 0, 4, 2, 1);

        addCommandSection(root);

        root.validate(this);
    }

    private void createMessageForSerialization() {
        Message myMessage = new Message();
        myMessage.message = "hello";
        JSONMessage = JSON.toJSONString(myMessage);
        writeToFile(JSONMessage);
    }

    private String getJsonMessage() {
        return JSONMessage;
    }

    private void loadSerialization() {
        String JsonMessage = getJsonMessage();
        if (JsonMessage.equals("not yet set")) {
            MacroButtons.sayMessage("not yet set!");
        } else {
            String deserializedMessage = JSON.parseObject(JsonMessage, String.class);
            MacroButtons.sayMessage(deserializedMessage);
        }

    }

    private void writeToFile(String jsonMessage) {
        try {
            // these both write to the correct location
            file = new FileWriter("commands.json");
            // file = new FileWriter(MinecraftClient.getInstance().runDirectory + "/command.json");
            file.write(jsonMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

    private int incrementNumber(int a, int b) {
        return a+b;
    }

    // Change background panel color to transparent black
    @Override
    public void addPainters() {
        super.addPainters();
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(0x4D000000));
    }


}
