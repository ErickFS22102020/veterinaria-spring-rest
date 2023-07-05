package idat.proyecto.veterinaria.response;

import java.util.HashMap;
import java.util.Map;

public class Response {

    public static Map<String, Object> createMap(String message, int id) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("id", id);
        return map;
    }
}