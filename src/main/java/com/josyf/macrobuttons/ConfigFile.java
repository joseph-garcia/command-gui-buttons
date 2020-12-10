package com.josyf.macrobuttons;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.josyf.macrobuttons.gui.ButtonGUI;
import io.netty.channel.group.ChannelGroupFuture;
import net.minecraft.client.MinecraftClient;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.logging.Level;

public class ConfigFile {


    static JSONParser parser = new JSONParser();

    private static FileWriter fileWriter;
    private static FileReader fileReader;
    private static File file;
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
            //String deserializedMessage = JSON.parseObject(JSONConfig, String.class);
            String deserializedMessage = readFile();
            MacroButtons.sayMessage(deserializedMessage);
        }
    }

    private static void writeToFile(String jsonMessage) {
        try {
            // these both write to the correct location
            fileWriter = new FileWriter("commands.json");
            // file = new FileWriter(MinecraftClient.getInstance().runDirectory + "/command.json");
            fileWriter.write(jsonMessage);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static String readFile() {
        try {
            Object obj = parser.parse(new FileReader("commands.json"));
            String jsonString = JSONObject.toJSONString(obj);
            System.out.println(jsonString);
            return jsonString;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Not yet initialized yo";
    }
}
