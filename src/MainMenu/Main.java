package MainMenu;
import AVLTree.OrderAVLTree;
import BinaryTree.BinaryTreeOrder;
import generarDatos.GenerateData;
import utils.CustomReturn;
import utils.Submenus;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            // Display the menu
            System.out.println("Menu Principal");
            System.out.println("1. Generar datos random");
            System.out.println("2. Ordenar datos");
            System.out.println("3. Salir");

            // Prompt user for choice
            System.out.println("Ingrese su opcion: ");
            System.out.println("------------------------------------------------------------------------------");
            int choice = scanner.nextInt();
            //! Generate data
            GenerateData generateData = new GenerateData();
            Submenus submenu = new Submenus();
            OrderAVLTree avlThree = new OrderAVLTree();
            BinaryTreeOrder binaryTree = new BinaryTreeOrder();
            // Handle user's choice
            switch (choice) {
                case 1:
                    int option = submenu.generateIntDataOption(scanner);
                    if(option != 0){
                        generateData.generate(option);
                    }
                    break;
                case 2:
                    CustomReturn optionsSelected = submenu.getOrderThreeOption(scanner);
                    System.out.println(optionsSelected.getFileName());
                    System.out.println(optionsSelected.getFileOrder());
                    if(optionsSelected.getFileOrder() == 1){
                        binaryTree.readDataFromFile(optionsSelected.getFileName());
                        binaryTree.writeOrderedDataToFile(optionsSelected.getFileName());
                    }
                    if( optionsSelected.getFileOrder() == 2){
                        avlThree.orderAVLTree(optionsSelected.getFileName());
                    }
                    break;
                case 3:
                    System.out.println("Guardando datos...");
                    exit = true; // Set exit flag to true
                    break;
                default:
                    System.out.println("Opcioin invalida. por favor ingrese un numero entre 1 y 3.");
            }
        }

        scanner.close();
    }
}
