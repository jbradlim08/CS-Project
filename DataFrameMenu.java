
import java.util.*;
import java.io.*;

public class DataFrameMenu {
    static DataFrame dataFrame = new DataFrame();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        boolean loop = true;

        while (loop) {
            if (dataFrame.getFile() == null) {

                System.out.println("DataFrame Available:");
                System.out.println("Command Options:");
                System.out.println("i: import a new CSV");
                System.out.println("q: Quit");

            } else {

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
            }

            String choice = scan.next();

            switch (choice.charAt(0)) {
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
                    loop = false;
                    break;
            }

        }
    }

}
