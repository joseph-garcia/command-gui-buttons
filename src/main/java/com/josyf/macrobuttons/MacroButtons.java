package com.josyf.macrobuttons;



import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class MacroButtons implements ModInitializer {

    public static final String MOD_ID = "mgbuttons";


    @Override
    public void onInitialize() {
        assignGuiToKey();
    }

    private void assignGuiToKey() {

        System.out.println("I'm getting here");

        KeyBinding keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.macrobuttons.opengui", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_G, // The keycode of the key
                "gui.macrobuttons.mgbuttons" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                // client.player.sendMessage(new LiteralText("Key 1 was pressed!"), false);
                MinecraftClient.getInstance().openScreen(new ExampleScreen(new ExampleGui()));
                //printMessage();
            }
        });
    }

    public class ExampleScreen extends CottonClientScreen {
        public ExampleScreen(GuiDescription description) {
            super(description);
        }
    }

    public class ExampleGui extends LightweightGuiDescription {
        public ExampleGui() {
            WGridPanel root = new WGridPanel();
            setRootPanel(root);
            root.setSize(256, 240);

            // WSprite icon = new WSprite(new Identifier("minecraft:textures/item/redstone.png"));
            // root.add(icon, 0, 2, 1, 1);

            // example button to play with
            WButton button = new WButton(new TranslatableText("gui.examplemod.examplebutton"));
            button.setOnClick(() -> {
               printMessage();
            });
            root.add(button, 0, 3, 4, 1);

            WLabel label = new WLabel(new LiteralText("Test"), 0xFFFFFF);
            root.add(label, 0, 4, 2, 1);

            root.validate(this);
        }
    }

    // player can run a command here
    private void printMessage() {
        MinecraftClient.getInstance().player.sendChatMessage("/seed");
    }

}

