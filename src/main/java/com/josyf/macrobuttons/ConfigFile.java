package com.josyf.macrobuttons;

import com.alibaba.fastjson.JSON;
import com.cedarsoftware.util.io.JsonWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

public class ConfigFile {

    static JSONParser parser = new JSONParser();
    private static FileWriter fileWriter;

    // Read commands.json, convert it to an array, and append A JSON OBJECT
    public static void appendToFile(JSONObject jsonObject) {
        try {
            Object obj = parser.parse(new FileReader("commands.json"));
            JSONArray array = (JSONArray) obj;
            array.add(jsonObject);
            writeToFile(array);
        } catch (IOException e) {
            System.out.println("Commands.json doesn't exist. Creating one...!");
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            writeToFile(jsonArray);
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
    public static ArrayList<JSONObject> getArrayFromJsonFile() {
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
                if (i >= 19) break;
            }
            return commandObjects;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addObjectToCommList(JSONObject jsonObject) {
        ArrayList<JSONObject> commListCopy = MacroButtons.getMasterCommList();
        commListCopy.add(jsonObject);
        MacroButtons.setMasterCommList(commListCopy);
    }


}
