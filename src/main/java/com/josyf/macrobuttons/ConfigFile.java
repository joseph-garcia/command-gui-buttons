package com.josyf.macrobuttons;

import com.alibaba.fastjson.JSON;
import com.josyf.macrobuttons.gui.ButtonGUI;

import java.io.FileWriter;
import java.io.IOException;

public class ConfigFile {

    private static FileWriter file;
    //private static final String configSettings = ButtonGUI.getConfig();

    public static void serializeCommand() {
        CommandObject newCommand = new CommandObject();
        newCommand.message = "hello";
        ButtonGUI.setConfig(JSON.toJSONString(newCommand));
        writeToFile(ButtonGUI.getConfig());
    }

    public static void loadSerialization() {
        String JSONConfig = ButtonGUI.getConfig();
        if (JSONConfig == null) {
            MacroButtons.sayMessage("GUI Configuration not yet initialized!");
        } else {
            String deserializedMessage = JSON.parseObject(JSONConfig, String.class);
            MacroButtons.sayMessage(deserializedMessage);
        }
    }

    private static void writeToFile(String jsonMessage) {
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
}
