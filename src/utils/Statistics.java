package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Statistics {

    int numRecords;
    String memoryUsed, timeTaken, orderType;
    Path currentPath = Paths.get(System.getProperty("user.dir"));
    File folder = new File(currentPath.toFile(), "statistics");
    public Statistics(int numRecords, String orderType, String memoryUsed, String  timeTaken){
        this.numRecords = numRecords;
        this.orderType = orderType;
        this.memoryUsed = memoryUsed;
        this.timeTaken = timeTaken;
    }

    public void generateReport(){
        try {
            if (!folder.exists()) {
                folder.mkdir(); // Create the "registros" folder if it doesn't exist
            }
            File file = new File(folder, "results.txt");

            // Date generation
            LocalDateTime now = LocalDateTime.now();
            // Define the desired date and time format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy 'a las' hh:mm a");
            // Format the date and time using the formatter
            String formattedDateTime = now.format(formatter);

            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.write("Resumen realizado en "+ formattedDateTime + "\n"); //! sf
            fileWriter.write("Tipo de orden: " + this.orderType+ "\n");
            fileWriter.write("Memoria usada: "+ this.memoryUsed+ "\n");
            fileWriter.write("Tiempo de ejecucion: "+ this.timeTaken+ "\n");
            fileWriter.write("Cantidad de elementos ordenados: "+ this.numRecords+ "\n");
            fileWriter.write("**********************************************************"+ "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
