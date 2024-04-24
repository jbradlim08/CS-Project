
import java.util.*;
import java.io.*;

public class DataFrameMenu {
    static DataFrame dataFrame = new DataFrame();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean loop = false;

        System.out.println("DataFrame Available:");
        System.out.println("Command Options:");
        System.out.println("i: import a new CSV");
        System.out.println("q: Quit");
        String choice = scan.next();
        choices(choice.charAt(0));

        while (loop) {
            System.out.println("DataFrame Available: ");
            System.out.println("Active: " + dataFrame.getColumnHeaders());

            System.out.println("i: import a new CSV");
            System.out.println("c: change the active DataFrame");
            System.out.println("a: average a column");
            System.out.println("m: find min for a column");
            System.out.println("x: find max for a column");
            System.out.println("f: freq table for a column");
            System.out.println("s: subset by a column value");
            System.out.println("e: export the active DataFrame to CSV");
            System.out.println("q: Quit");
            choice = scan.next();
            choices(choice.charAt(0));

            if (choice.equals("q")) {
                loop = false;
            }
        }
    }

    public static void choices(char choice) {
        switch (choice) {
            case 'i':
                dataFrame.importCSV();
                break;
            case 'c':
                break;
            case 'a':
                break;
            case 'm':
                break;
            case 'x':
                break;
            case 'f':
                break;
            case 's':
                break;
            case 'z':
                break;
            case 'q':
                break;
        }
    }
}
/*
 * public static void options() {
 * DataFrame dataFrame = new DataFrame();
 * 
 * System.out.println("DataFrame Available: ");
 * System.out.println("Active: " + dataFrame.getColumnHeaders());
 * 
 * System.out.println("Command options:");
 * System.out.println("i: import a new CSV");
 * System.out.println("i: change the active DataFrame");
 * System.out.println("i: average a column");
 * System.out.println("i: find min for a column");
 * System.out.println("i: find max for a column");
 * System.out.println("i: freq table for a column");
 * System.out.println("i: subset by a column value");
 * System.out.println("i: export the active DataFrame to CSV");
 * System.out.println("i: Quit");
 * 
 * }
 * 
 * }
 */
