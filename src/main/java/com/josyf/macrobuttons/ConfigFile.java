package com.josyf.macrobuttons;

import com.alibaba.fastjson.JSON;
import com.cedarsoftware.util.io.JsonWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.josyf.macrobuttons.gui.ButtonGUI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class ConfigFile {


    static JSONParser parser = new JSONParser();

    private static FileWriter fileWriter;

    public static void serializeCommand() {
        // CREATE OBJECT
        CommandObject newCommand = new CommandObject("Say hello", "hello command");

        // add new object to config
        appendToFile(newCommand);
    }

    public static void loadSerialization() {
        String JSONConfig = ButtonGUI.getConfig();
        if (JSONConfig == null) {
            MacroButtons.runCommand("GUI Configuration not yet initialized!");
        } else {
            System.out.println(readFile());
        }
    }

    // if commands.json exists, read it, convert it to an array, and append
    // DEPRECATED PROBABLY DELETABLE
    private static void appendToFile(CommandObject commandObject) {
        JSONArray jsonArray = new JSONArray();
        try {
            // if commands.json exists, read it, convert it to an array, and append
            Object obj = parser.parse(new FileReader("commands.json"));

            JSONArray array = (JSONArray) obj;
            JSONObject obj2 = (JSONObject)array.get(0);

        } catch (IOException e) {
            System.out.println("Commands.json doesn't exist. Creating one...");
            // create json
            jsonArray.add(commandObject);
            writeToFile(jsonArray);
        } catch (ParseException e) {
            System.out.println("Something went wrong |o|");
        }
    }

    // if command.json exists, read it, convert it to an array, and append A JSON OBJECT
    public static void appendToFile(JSONObject jsonObject) {
        try {
            Object obj = parser.parse(new FileReader("commands.json"));
            JSONArray array = (JSONArray) obj;
            array.add(jsonObject);
            writeToFile(array);
            MacroButtons.initArray();
        } catch (IOException e) {
            System.out.println("Commands.json doesn't exist. Creating one...!");
            // create json
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            writeToFile(jsonArray);
            MacroButtons.initArray();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // overwrites current commands.json w/ updated jsonArray
    private static void writeToFile(JSONArray jsonArray) {
        try {
            fileWriter = new FileWriter("commands.json");
            String jArrayToString = JSON.toJSONString(jsonArray);
            String formattedJson = JsonWriter.formatJson(jArrayToString);
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

    // parses commands.json into array, loop through array and add JSONObjects. returns array
    public static ArrayList<JSONObject> initArray() {
        ArrayList<JSONObject> commandObjects = new ArrayList<>();
        try {
            // assign array to JSONArray using our commands.json as an object
            Object obj = parser.parse(new FileReader("commands.json"));
            JSONArray array = (JSONArray) obj;
            // so "array" is now a JSONArray full of JSONObjects
            // now we will iterate through the array and add COs to our local CO array
            for (int i = 0; i < array.size(); i++) {
                JSONObject childObject = (JSONObject)array.get(i);
                commandObjects.add(childObject);
                System.out.println(i);
            }
            return commandObjects;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // DEPRECATED CAN PROBABLY DELETE
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
            String jsonString = obj.toString();
            //String jsonString = JSONObject.toJSONString(obj);
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
