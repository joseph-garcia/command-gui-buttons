package com.josyf.macrobuttons.gui;

import com.josyf.macrobuttons.MacroButtons;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WTextField;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class ButtonGUI extends LightweightGuiDescription {
    public ButtonGUI() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(256, 240);

        // WSprite icon = new WSprite(new Identifier("minecraft:textures/item/redstone.png"));
        // root.add(icon, 0, 2, 1, 1);

        // example button to play with
        WButton button = new WButton(new TranslatableText("Button"));
        button.setOnClick(() -> {
            MacroButtons.printMessage();
        });
        root.add(button, 0, 3, 4, 1);

        WLabel label = new WLabel(new LiteralText("Test"), 0xFFFFFF);
        root.add(label, 0, 4, 2, 1);

        addCommandSection(root);

        root.validate(this);
    }

    private void addCommandSection(WGridPanel root) {
        // Add text field for command entry
        WTextField textField = new WTextField();
        root.add(textField, 6, 12, 6, 1);

        // Add button for command entry
        WButton addCmdBtn = new WButton(new TranslatableText("+"));
        addCmdBtn.setOnClick(() -> {
            MacroButtons.addGUIButton();
        });
        root.add(addCmdBtn, 13, 12, 1, 1);
    }

    // Change background panel color to transparent black
    @Override
    public void addPainters() {
        super.addPainters();
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(0x4D000000));
    }
}
