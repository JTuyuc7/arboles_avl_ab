package AVLTree;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.util.Iterator;

// Main class
public class Main {
    public static void main(String[] args) {
        try {
            long startTime = System.nanoTime(); // Measure start time
            // Read JSON file
            FileReader reader = new FileReader("registros.json");
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            // Construct AVL Tree
            AVLTree tree = new AVLTree();
            Iterator<JSONObject> iterator = jsonArray.iterator();

            //# Calcular la memoria
            Runtime runtime = Runtime.getRuntime();
            runtime.gc();
            long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
            while (iterator.hasNext()) {
                JSONObject obj = iterator.next();
                String carnet = (String) obj.get("carnet");
                int keyValue = carnet.hashCode(); // Using hashCode as the key
                tree.root = tree.insert(tree.root, keyValue, obj);
            }
            runtime.gc();
            long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
            // Measure memory usage after constructing AVL tree
            long memoryUsed = memoryAfter - memoryBefore;
            System.out.println(formatMemoryUsage(memoryUsed));

            // Write ordered data to a file as a JSON array
            FileWriter fw = new FileWriter("output.json");
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("[\n");
            tree.writeJSON(tree.root, writer);
            writer.write("]");
            writer.close();

            System.out.println("Data ordered and written to output.json successfully.");
            long endTime = System.nanoTime(); // Measure end time
            long durationSeconds = (endTime - startTime) / 1_000_000_000;
            long hours = durationSeconds / 3600;
            long minutes = (durationSeconds % 3600) / 60;
            long seconds = durationSeconds % 60;

            String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
            System.out.println("Time taken: " + formattedTime);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // Human readable memory
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

    // Method to measure memory usage
    private static long measureMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // Run garbage collection to clean up memory
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        // Construct a temporary object to force memory allocation
        long[] temp = new long[10_000];
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        // Deallocate temporary object
        temp = null;
        runtime.gc(); // Run garbage collection to clean up memory
        return memoryAfter - memoryBefore;
    }
}
