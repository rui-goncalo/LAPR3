package lapr.project.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DateUtils {
    /**
     * Reads a Date from user input with error checking
     *
     * @param sc scanner to read input from the user
     * @return date read from valid user input or null otherwise
     */
    public static LocalDateTime readDate(Scanner sc, String msg) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.print("Format: dd/MM/yyyy HH:mm\n");
        System.out.print(msg);
        LocalDateTime dateTime;

        String str = sc.nextLine();

        try {
            dateTime = LocalDateTime.parse(str, format);
        } catch (Exception e) {
            System.out.print("Invalid date.\n");
            return null;
        }
        return dateTime;
    }
}