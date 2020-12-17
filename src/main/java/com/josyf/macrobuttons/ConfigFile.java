package com.josyf.macrobuttons;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
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
    private static FileReader fileReader;
    private static File file;
    //private static final String configSettings = ButtonGUI.getConfig();

    public static void serializeCommand() {

        // CREATE OBJECT
        CommandObject newCommand = new CommandObject("Say hello", "hello command");

        // add new object to config
        appendToFile(newCommand);

        // set instance to real config

        //writeToFile(ButtonGUI.getConfig());
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
    // if commands.json exists, read it, convert it to an array, and append
    private static void appendToFile(CommandObject commandObject) {
        JSONArray jsonArray = new JSONArray();
        try {
            //jsonArray = (JSONArray) parser.parse(new FileReader("commands.json"));

            // if commands.json exists, read it, convert it to an array, and append
            Object obj = parser.parse(new FileReader("commands.json"));
            //ArrayList array = (ArrayList) parser.parse(obj.toString());
            //array.add(commandObject);
            //writeToFile(array);
            System.out.println("Here is the object: " + obj);
            System.out.println(obj.getClass());

            JSONArray array = (JSONArray) obj;
            System.out.println("length of array: " + array.size());
            JSONObject obj2 = (JSONObject)array.get(0);
            System.out.println("Qu'est-ce que c'est?");
            System.out.println(obj2.get("name"));



            System.out.println("obj's class is: " + obj.getClass());
            System.out.println("obj2's class is: " + obj2.getClass());
            System.out.println("Array size: " + array.size());

            System.out.println(MacroButtons.masterCommList);

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
                //String name = childObject.get("name").toString();
                //String command = childObject.get("command").toString();
                //CommandObject commandObject = new CommandObject(name, command);
                //commandObjects.add(commandObject);
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
