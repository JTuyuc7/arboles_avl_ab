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
        long startTimeDataGeneration = System.nanoTime(); // Measure start time for data generation
        List<JSONObject> registros = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 1500000; i++) {
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

        long endTimeDataGeneration = System.nanoTime(); // Measure end time for data generation
        long durationDataGeneration = (endTimeDataGeneration - startTimeDataGeneration) / 1_000_000; // Time in milliseconds
        long durationHours = durationDataGeneration / (60 * 60 * 1000);
        long durationMinutes = (durationDataGeneration % (60 * 60 * 1000)) / (60 * 1000);
        long durationSeconds = (durationDataGeneration % (60 * 1000)) / 1000;
        String formattedTime = String.format("%02d:%02d:%02d", durationHours, durationMinutes, durationSeconds);
        System.out.println("Data generation time: " + formattedTime);

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

            // Measure start time for data processing
            long startTimeDataProcessing = System.nanoTime();

            // Data processing logic (AVL tree construction, etc.) would go here

            // Measure end time for data processing
            long endTimeDataProcessing = System.nanoTime();
            long durationDataProcessing = (endTimeDataProcessing - startTimeDataProcessing) / 1_000_000; // Time in milliseconds
            long processingHours = durationDataProcessing / (60 * 60 * 1000);
            long processingMinutes = (durationDataProcessing % (60 * 60 * 1000)) / (60 * 1000);
            long processingSeconds = (durationDataProcessing % (60 * 1000)) / 1000;
            formattedTime = String.format("%02d:%02d:%02d", processingHours, processingMinutes, processingSeconds);
            System.out.println("Data processing time: " + formattedTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
