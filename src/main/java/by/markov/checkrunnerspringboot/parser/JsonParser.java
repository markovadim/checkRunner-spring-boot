package by.markov.checkrunnerspringboot.parser;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@Component
@RequiredArgsConstructor
public class JsonParser {

    private final StringBuilder jsonString;


    public void serialize(Object object) throws IOException {
        jsonString.append("{");
        try {
            convertToJson(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonString.append("}");
        writeToFile(jsonString.toString());
    }

    private void convertToJson(Object ob) throws IllegalAccessException, IOException {
        Class<?> cl = ob.getClass();
        StringJoiner joiner = new StringJoiner(",");

        for (Field field : cl.getDeclaredFields()) {
            field.setAccessible(true);

            if ((field.getType().isPrimitive()) && (!field.getType().getTypeName().contains("char"))) {
                joiner.add("\"" + field.getName() + "\":" + field.get(ob));
            } else if ((field.getType().getSimpleName().equals("String")) || (field.getType().getSimpleName().equals("char"))) {
                joiner.add("\"" + field.getName() + "\":\"" + field.get(ob) + "\"");
            } else if ((field.getType().isArray()) || (field.get(ob) instanceof List<?>)) {
                joiner.add(parseArray(field.get(ob), field.getName()));
            } else if (field.get(ob) instanceof Map<?, ?>) {
                joiner.add(parseMap(field.get(ob), field.getName()));
            } else serialize(field.get(ob));
        }
        jsonString.append(joiner);
    }

    private String parseArray(Object ob, String name) {
        StringJoiner arrayJoiner = new StringJoiner(",");
        String str = "\"" + name + "\":[";
        for (int i = 0; i < Array.getLength(ob); i++) {
            if ((!ob.getClass().getSimpleName().contains("char")) && (!ob.getClass().getSimpleName().contains("String"))) {
                arrayJoiner.add(Array.get(ob, i).toString());
            } else arrayJoiner.add("\"" + Array.get(ob, i) + "\"");
        }
        str += arrayJoiner + "]";
        return str;
    }

    private String parseMap(Object ob, String name) {
        Map<?, ?> map = (Map<?, ?>) ob;
        StringJoiner mapJoiner = new StringJoiner(",");
        String str = "\"" + name + "\":{";

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            mapJoiner.add("\"" + entry.getKey() + "\":\"" + entry.getValue() + "\"");
        }
        str += mapJoiner + "}";
        return str;
    }

    public void writeToFile(String result) throws IOException {
        File file = new File("json_objects.json");
        try (FileWriter fw = new FileWriter(file)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw.append(result);
        }
    }
}

