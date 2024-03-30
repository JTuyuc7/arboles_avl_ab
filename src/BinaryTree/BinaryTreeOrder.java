package BinaryTree;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BinaryTreeOrder {
    Path currentPath = Paths.get(System.getProperty("user.dir"));

    TreeNode root; // The root of the BST

    /**
     * Constructor to initialize an empty binary search tree.
     */
    public BinaryTreeOrder() {
        root = null;
    }

    /**
     * Method to insert a node into the BST.
     *
     * @param key The key of the node to be inserted.
     * @param obj The JSON object associated with the key to be inserted.
     */
    public void insert(String key, JSONObject obj) {
        root = insertRec(root, key, obj);
    }

    /**
     * Recursive method to insert a node into the BST.
     *
     * @param root The root of the subtree where insertion is to be done.
     * @param key  The key of the node to be inserted.
     * @param obj  The JSON object associated with the key to be inserted.
     * @return The root of the modified subtree after insertion.
     */
    private TreeNode insertRec(TreeNode root, String key, JSONObject obj) {
        if (root == null) {
            root = new TreeNode(key, obj);
            return root;
        }

        // Extract the "carnet" field from the current node and the key to be inserted
        String nodeCarnet = (String) root.data.get("carnet");

        // Compare the "carnet" field values lexicographically
        int compareResult = key.compareTo(nodeCarnet);

        // Recursively insert into the left or right subtree based on the comparison result
        if (compareResult < 0) {
            root.left = insertRec(root.left, key, obj);
        } else if (compareResult > 0) {
            root.right = insertRec(root.right, key, obj);
        }

        return root;
    }

    /**
     * Method to read data from a JSON file and construct the binary tree.
     *
     * @param fileName The name of the JSON file containing the data.
     */
    public void readDataFromFile(String fileName) {
        long startTime = System.nanoTime(); // Measure start time

        JSONParser parser = new JSONParser();
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        try  {
//            Path currentPath = Paths.get(System.getProperty("user.dir"));
            String filePath = currentPath.resolve("registros").resolve(fileName).toString();
            FileReader reader = new FileReader(filePath);
            Object obj = parser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                String key = (String) jsonObject.get("carnet");
                insert(key, jsonObject);
            }
            System.out.println("Data has been read from file: " + fileName);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        runtime.gc();
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        // Measure memory usage after constructing AVL tree
        long memoryUsed = memoryAfter - memoryBefore;
        System.out.println(formatMemoryUsage(memoryUsed)+" Memoria usada.");
        long endTime = System.nanoTime(); // Measure end time
        long durationSeconds = (endTime - startTime) / 1_000_000_000;
        long hours = durationSeconds / 3600;
        long minutes = (durationSeconds % 3600) / 60;
        long seconds = durationSeconds % 60;

        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        System.out.println("En tiempo de: " + formattedTime);
    }

    /**
     * Method to write ordered data to a file using preorder traversal.
     *
     * @param fileName The name of the file to write the ordered data.
     */
    public void writeOrderedDataToFile(String fileName) {
        List<JSONObject> orderedObjects = new ArrayList<>();
        preorder(root, orderedObjects); // Get ordered JSON objects

        File folder = new File(currentPath.toFile(), "binaryOrder");
        if (!folder.exists()) {
            folder.mkdir(); // Create the "binaryOrder" folder if it doesn't exist
        }
        File file = new File(folder, fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(orderedObjects);
            writer.write(jsonArray.toJSONString());
            System.out.println("Ordered data has been written to file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recursive method to perform preorder traversal and collect JSON objects in order.
     *
     * @param root           The root of the subtree to be traversed.
     * @param orderedObjects The list to collect ordered JSON objects.
     */
    private void preorder(TreeNode root, List<JSONObject> orderedObjects) {
        if (root != null) {
            orderedObjects.add(root.data);
            preorder(root.left, orderedObjects);
            preorder(root.right, orderedObjects);
        }
    }

    private static String formatMemoryUsage(long bytes) {
        double kb = bytes / 1024.0;
        double mb = kb / 1024.0;
        double gb = mb / 1024.0;

        if (gb >= 1.0) {
            return String.format("%.2f GB", gb);
        } else if (mb >= 1.0) {
            return String.format("%.2f MB", mb);
        } else if (kb >= 1.0) {
            return String.format("%.2f KB", kb);
        } else {
            return bytes + " bytes";
        }
    }
}
