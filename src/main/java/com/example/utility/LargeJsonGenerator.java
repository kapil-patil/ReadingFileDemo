package com.example.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class LargeJsonGenerator {

    public static void main(String[] args) {
        // Create an ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Create a root array node
        ArrayNode rootArray = objectMapper.createArrayNode();

        // Generate a large number of JSON objects
        for (int i = 0; i < 1000000; i++) { // Adjust the number as needed
            // Create a new JSON object
            ObjectNode jsonObject = objectMapper.createObjectNode();
            jsonObject.put("name", "Baledung");
            jsonObject.put("area", "");
            jsonObject.put("author", "auth");
            jsonObject.put("id",i);
            
            List<String> list1 = new ArrayList<String>();
            list1.add("java");
            list1.add("kotlin");
            list1.add("cs");
            list1.add("linux");
            
            
            List<String> list2 = new ArrayList<String>();
            list2.add("Bucharest");
            list2.add("Romania");
            list2.add("Pune");
            list2.add("India");
            
			/*
			 * JSONArray array1 = new JSONArray();
			 * 
			 * for (int j = 0; j < list1.size(); j++) { array1.put(list1.get(j)); }
			 * 
			 * JSONArray array2 = new JSONArray(); for (int k = 0; k < list2.size(); k++) {
			 * array2.put(list2.get(k)); }
			 */
            
            
            ArrayNode arrayNode = jsonObject.putArray("topics");
            
            for (String item : list1) {
                arrayNode.add(item);
            }
            
            JSONObject arrayElementOneArrayElementOne = new JSONObject();
            arrayElementOneArrayElementOne.put("city", "Bucharest");
            arrayElementOneArrayElementOne.put("country", "Romania");
            
			/*
			 * JSONArray arrayElementOneArray = new JSONArray();
			 * 
			 * 
			 * arrayElementOneArray.put(arrayElementOneArrayElementOne);
			 * 
			 * JSONObject arrayElementOne = new JSONObject(); arrayElementOne.put("address",
			 * arrayElementOneArray);
			 * 
			 * 
			 * jsonObject.put("address", arrayElementOneArray);
			 */
            
            
			/*
			 * ArrayNode arrayNode2 = jsonObject.putArray("address");
			 * 
			 * for (String item : list2) { arrayNode2.add(item); }
			 */
           

            // Add the JSON object to the root array
            rootArray.add(jsonObject);
        }

        // Write the JSON array to a file
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("D:\\PSRE\\output\\large_file.json"), rootArray);
            System.out.println("JSON file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

