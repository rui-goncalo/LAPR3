package lapr.project.ui;


import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DateMenu {
    /**
     * Reads a DateTime from user input with error checking
     *
     * @param sc scanner to read input from the user
     * @return date read from valid user input or null otherwise
     */
    public static LocalDateTime readDate(Scanner sc, String msg) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.print("Format: dd/MM/yyyy HH:mm\n");
        LocalDateTime dateTime;

        do {
            System.out.print(msg);
            String str = sc.nextLine();
            try {
                if (str.equals("null"))
                    return null;
                else
                    dateTime = LocalDateTime.parse(str, format);
            } catch (Exception e) {
                System.out.println("Your date is invalid.\n");
                dateTime = null;
            }
        } while (dateTime == null);

        return dateTime;
    }


    /**
     * Reads a Date from user input with error checking
     *
     * @param sc scanner to read input from the user
     * @return date read from valid user input or null otherwise
     */

    public static LocalDate readDateSQL(Scanner sc, String msg) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print("dd/MM/yyyy\n");
        LocalDate dateTime;

        do {
            System.out.print(msg);
            String str = sc.nextLine();
            try {
                if (str.equals("null"))
                    return null;
                else
                    dateTime = LocalDate.parse(str, format);
            } catch (Exception e) {
                System.out.println("Your date is invalid.\n");
                dateTime = null;
            }
        } while (dateTime == null);

        return dateTime;
    }
}