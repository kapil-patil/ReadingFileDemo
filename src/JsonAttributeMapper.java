import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonAttributeMapper {

    // Recursive method to traverse the JSON and collect all attributes
    public static void getAttributes(JsonNode node, Map<String, Object> attributeMap, String parentKey) {
        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            
            String fullKey = (parentKey.isEmpty()) ? key : parentKey + "." + key;
            
            if (value.isObject()) {
                // If the value is an object, recursively get its attributes
                getAttributes(value, attributeMap, fullKey);
            } else if (value.isArray()) {
                // If the value is an array, loop through each element and get attributes
                for (int i = 0; i < value.size(); i++) {
                    getAttributes(value.get(i), attributeMap, fullKey + "[" + i + "]");
                }
            } else {
                // If the value is a primitive, add it to the map
                attributeMap.put(fullKey, value.asText());
            }
        }
    }

    public static Map<String, Object> getJsonAttributes(String jsonString) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        Map<String, Object> attributeMap = new HashMap<>();

        getAttributes(rootNode, attributeMap, "");

        return attributeMap;
    }

    public static void main(String[] args) {
        String jsonString = "{ \"name\": \"John\", \"age\": 30, \"address\": { \"street\": \"123 Main St\", \"city\": \"New York\" }, \"phones\": [ \"123-456-7890\", \"987-654-3210\" ] }";

        try {
            Map<String, Object> attributes = getJsonAttributes(jsonString);
            attributes.forEach((key, value) -> System.out.println(key + " : " + value));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
