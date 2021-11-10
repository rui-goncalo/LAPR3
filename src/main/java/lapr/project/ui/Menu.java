package lapr.project.ui;

import java.util.Scanner;

public class Menu {

    boolean exit;

    public void runMenu() {
        printHeader();
        while (!exit) {
            printMenu();
            int choice = getInput();
        }
    }

    private void printHeader() {

        System.out.println("+-----------------------------+");
        System.out.println("|         Welcome to our       ");
        System.out.println("|        Menu Application      ");
        System.out.println("+-----------------------------+");
    }

    private void printMenu() {
        System.out.println("Please make a selection: ");
        System.out.println("1) Import Files");
        System.out.println("0) Exit");
    }

    private int getInput() {
        Scanner scan = new Scanner(System.in);
        int choice = -1;
        while (choice < 1 || choice > 2) {
            try {
                System.out.println("\nEnter your choice: ");
                choice = Integer.parseInt(scan.nextLine());
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid selection. Please try again.");
            }
        }
        return choice;
    }
}
