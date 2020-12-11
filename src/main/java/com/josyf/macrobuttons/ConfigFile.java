package com.josyf.macrobuttons;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cedarsoftware.util.io.JsonWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.josyf.macrobuttons.gui.ButtonGUI;
import io.netty.channel.group.ChannelGroupFuture;
import net.minecraft.client.MinecraftClient;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.util.logging.Level;

public class ConfigFile {


    static JSONParser parser = new JSONParser();

    private static FileWriter fileWriter;
    private static FileReader fileReader;
    private static File file;
    //private static final String configSettings = ButtonGUI.getConfig();

    public static void serializeCommand() {

        // CREATE OBJECT
        CommandObject newCommand = new CommandObject();
        newCommand.name = "Say Hello";
        newCommand.command = "Hello";

        // add new object to config
        appendToFile(newCommand);

        // set instance to real config
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

    private static void appendToFile(CommandObject commandObject) {
        JSONArray jsonArray = new JSONArray();
        try {
            //jsonArray = (JSONArray) parser.parse(new FileReader("commands.json"));

            // if commands.json exists, read it, convert it to an array, and append
            Object obj = parser.parse(new FileReader("commands.json"));
            JSONArray array = (JSONArray) parser.parse(obj.toString());
            array.add(commandObject);
            writeToFile(array);
            System.out.println("attempting to append");


            //System.out.println(obj.getClass());
            System.out.println("Array size: " + array.size());

        } catch (IOException e) { // commands.json doesn't exist
            System.out.println("catch 1");
            // create json
            jsonArray.add(commandObject);
            writeToFile(jsonArray);
        } catch (ParseException e) {
            System.out.println("catch 2");

        }


    }

    private static void writeToFile(JSONArray jsonArray) {
        try {
            fileWriter = new FileWriter("commands.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jArrayToString = JSON.toJSONString(jsonArray);
            String formattedJson = JsonWriter.formatJson(jArrayToString);
            //fileWriter.write(jArrayToString);
            fileWriter.write(formattedJson);
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

    private static void writeToFile(String jsonMessage) {
        try {
            // these both write to the correct location
            fileWriter = new FileWriter("commands.json");
            // file = new FileWriter(MinecraftClient.getInstance().runDirectory + "/command.json");
            System.out.println("APPENDING!");

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
