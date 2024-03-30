package utils;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Submenus {

    public int generateIntDataOption(Scanner scanner) {
        int choice = 0;
        boolean validChoice = false;
        while (!validChoice) {
            // Prompt user for choice
            System.out.println("Seleccione la cantidad de datos que desea generar: ");
            System.out.println("1. 50k (50,000)");
            System.out.println("2. 300k (300,000)");
            System.out.println("3. 500K (500,000)");
            System.out.println("4. 750k (750,000)");
            System.out.println("5. 1.5m (1,500,000)");
            System.out.println("0. Exit");
            choice = scanner.nextInt();
            if (choice >= 0 && choice <= 5) {
                validChoice = true;
            } else {
                System.out.println("Opción inválida. Por favor, ingrese un número entre 0 y 5.");
            }
        }
        return choice;
    }

    public CustomReturn getOrderThreeOption(Scanner scanner){
        int choice = 0;
        boolean validChoice = false;
        String fileName = "";
        while (!validChoice) {
            // Prompt user for choice
            System.out.println("Seleccione el tipo de orden: ");
            System.out.println("1. Arbol Binario simple");
            System.out.println("2. Arbol (AVL)");
            System.out.println("0. Salir");
            choice = scanner.nextInt();
            if (choice >= 0 && choice <= 2) {
                fileName = getFileNameToUse(scanner);
                validChoice = true;
            } else {
                System.out.println("Opción inválida. Por favor, ingrese un número entre 0 y 2.");
            }
        }
        return new CustomReturn(fileName, choice);
    }


    private String getFileNameToUse(Scanner scanner) {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        File folder = new File(currentPath.toFile(), "registros");
        if (!folder.exists()) {
            System.out.println("Sin registros para usar");
            return "";
        }

        File[] files = folder.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("No hay archivos en el directorio 'registros'.");
            return "";
        }

        while (true) {
            System.out.println("Seleccione un archivo (0 para volver al menú principal):");
            for (int i = 0; i < files.length; i++) {
                System.out.println((i + 1) + ". " + files[i].getName());
            }

            int choice = getUserChoice(scanner);
            if (choice == 0) {
                return "";
            } else if (choice >= 1 && choice <= files.length) {
                return files[choice - 1].getName();
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }

    }

    private int getUserChoice(Scanner scanner) {
        System.out.print("Ingrese el número de archivo: ");
        return scanner.nextInt();
    }
}
