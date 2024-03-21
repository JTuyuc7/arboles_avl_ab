package generarDatos;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        List<JSONObject> registros = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            int year = random.nextInt(2024 - 1950 + 1) + 1950;
            int month = random.nextInt(12) + 1;
            int day = random.nextInt(28) + 1;
            String carnet = String.format("%d-%02d-%02d", year, month, day);
            String nombre = "Nombre" + random.nextInt(1000);
            String apellido = "Apellido" + random.nextInt(1000);
            JSONObject registro = new JSONObject();
            registro.put("carnet", carnet);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registros.add(registro);
        }

        try {
            // Get the current directory where the Java file is located
            Path currentPath = Paths.get(System.getProperty("user.dir"));

            // Create registros.json in the directory
            File file = new File(currentPath.toFile(), "registros.json");
            FileWriter fileWriter = new FileWriter(file);
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(registros);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
