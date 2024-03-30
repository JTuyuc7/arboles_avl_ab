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

/**
 * This class generates random data and saves it to a JSON file.
 */
public class GenerateData {

    /**
     * Method to generate and save random data.
     * @param numRecords Number of records to generate.
     */
    public void generate(int numRecords) {
        long startTimeDataGeneration = System.nanoTime(); // Measure start time for data generation
        List<JSONObject> registros = new ArrayList<>();
        NameSizeGenerator nameAndAmount = new NameSizeGenerator();
        Random random = new Random();
        // Generate random data
        for (int i = 0; i < nameAndAmount.getFileName(numRecords).getFileSize(); i++) {
            int year = random.nextInt(2024 - 1950 + 1) + 1950;
            int month = random.nextInt(12) + 1;
            int day = random.nextInt(28) + 1;
            String carnet = generateCarnet(); // Generate carnet string
            String nombre = "Nombre" + random.nextInt(1000);
            String apellido = "Apellido" + random.nextInt(1000);
            JSONObject registro = new JSONObject();
            registro.put("carnet", carnet);
            registro.put("nombre", nombre);
            registro.put("apellido", apellido);
            registros.add(registro);
        }

        // Save data to a JSON file
        try {
            Path currentPath = Paths.get(System.getProperty("user.dir"));
            File folder = new File(currentPath.toFile(), "registros");
            if (!folder.exists()) {
                folder.mkdir(); // Create the "registros" folder if it doesn't exist
            }
            File file = new File(folder, nameAndAmount.getFileName(numRecords).getFileName());
            FileWriter fileWriter = new FileWriter(file);
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(registros);
            fileWriter.write(jsonArray.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTimeDataGeneration = System.nanoTime(); // Measure end time for data generation
        // Calculate and print data generation time
        long durationDataGeneration = (endTimeDataGeneration - startTimeDataGeneration) / 1_000_000;
        System.out.println("Data generation time: " + durationDataGeneration + " milliseconds");
    }

    /**
     * Method to generate a random carnet string.
     * @return Randomly generated carnet string.
     */
    private String generateCarnet() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // Generate random characters
        for (int i = 0; i < 4; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }

        // Append year, month, and day
        sb.append("-").append(random.nextInt(100)); // Year
        sb.append("-").append(random.nextInt(100)); // Month
        sb.append("-").append(random.nextInt(100)); // Day

        return sb.toString();
    }
}
