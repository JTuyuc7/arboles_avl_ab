package AVLTree;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * Main class for constructing AVL tree from JSON data and writing ordered data to a file.
 */
public class OrderAVLThree {

    /**
     * Main method to read JSON data, construct AVL tree based on the "carnet" field,
     * and write ordered data to a file.
     *
     * @param fileName The name of the JSON file to process.
     */
    public void orderAVLThree(String fileName) {
        try {
            Path currentPath = Paths.get(System.getProperty("user.dir"));
            long startTime = System.nanoTime(); // Measure start time

            String filePath = currentPath.resolve("registros").resolve(fileName).toString();
            // Read JSON file
            FileReader reader = new FileReader(filePath);
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            // Construct AVL Tree based on "carnet" field
            AVLTree tree = new AVLTree();
            Iterator<JSONObject> iterator = jsonArray.iterator();

            // Calculate memory usage before constructing AVL tree
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

            // Insert JSON objects into AVL tree based on "carnet" field
            while (iterator.hasNext()) {
                JSONObject obj = iterator.next();
                String carnet = (String) obj.get("carnet");
                tree.root = tree.insert(tree.root, carnet, obj); // Using "carnet" as the key
            }
            runtime.gc();
            long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
            // Measure memory usage after constructing AVL tree
            long memoryUsed = memoryAfter - memoryBefore;
            System.out.println(formatMemoryUsage(memoryUsed));

            File folder = new File(currentPath.toFile(), "ordenados");
            if (!folder.exists()) {
                folder.mkdir(); // Create the "ordenados" folder if it doesn't exist
            }
            File file = new File(folder, fileName);

            // Write ordered data to a file as a JSON array
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("[\n");
            tree.writeJSON(tree.root, writer);
            writer.write("]");
            writer.close();

            System.out.println("Datos ordenados y guardados en ordenados/" + fileName);
            long endTime = System.nanoTime(); // Measure end time
            long durationSeconds = (endTime - startTime) / 1_000_000_000;
            long hours = durationSeconds / 3600;
            long minutes = (durationSeconds % 3600) / 60;
            long seconds = durationSeconds % 60;

            String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            System.out.println("Tiempo tomado: " + formattedTime);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Formats memory usage in a human-readable format.
     *
     * @param bytes The memory usage in bytes.
     * @return A string representing memory usage in a human-readable format.
     */
    private static String formatMemoryUsage(long bytes) {
        if (bytes < 1024) {
            return bytes + " bytes";
        } else if (bytes < 1024 * 1024) {
            return String.format("%.2f KB", bytes / 1024.0);
        } else if (bytes < 1024 * 1024 * 1024) {
            return String.format("%.2f MB", bytes / (1024.0 * 1024));
        } else {
            return String.format("%.2f GB", bytes / (1024.0 * 1024 * 1024));
        }
    }
}
