import java.util.*;
import java.io.*;

public class DataFrameMenu {

    static DataFrame dataFrame = new DataFrame();

    public static void main(String[] args) {
        boolean loop = true;
        Scanner scan = new Scanner(System.in);

        try {
            while (loop) {
                if (dataFrame.getFile() == null) {
                    System.out.println("DataFrame Available:");
                    System.out.println("Command Options:");
                    System.out.println("i: import a new CSV");
                    System.out.println("q: Quit");
                } else {
                    System.out.println(" ");

                    // print every available dataframe
                    String sum = "";
                    for (File s : dataFrame.getDataFrameList()) {
                        sum += dataFrame.removeFileExtension(s + "") + " "; // make the file to String without csv
                    }
                    System.out.println("DataFrame Available: " + sum);
                    System.out.println("Active: " + dataFrame.removeFileExtension(dataFrame.getFile().getName()) + " "
                            + dataFrame.getColumnHeaders()
                            + " " + dataFrame.getColumnDatatypes() + " Rows Count: " + dataFrame.getRowsCount());
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

                String choice = scan.nextLine();

                if (dataFrame.getFile() == null) {
                    switch (choice) {
                        case "i":
                            importCSV();
                            break;
                        case "q":
                            loop = false;
                            break;
                        default:
                            System.out.println("...no such option...");
                            break;
                    }
                } else {
                    switch (choice) {
                        case "i":
                            importCSV();
                            break;
                        case "c":
                            changeActiveCSV();
                            break;
                        case "a":
                            numericColumn(choice);
                            break;
                        case "m":
                            numericColumn(choice);
                            break;
                        case "x":
                            numericColumn(choice);
                            break;
                        case "f":
                            numericColumn(choice);
                            break;
                        case "s":
                            break;
                        case "z":
                            break;
                        case "q":
                            loop = false;
                            break;
                        default:
                            System.out.println("...no such option...");
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scan.close();
        }
    }

    public static void importCSV() {
        Scanner scan = new Scanner(System.in);
        boolean b = true;
        while (b) {
            System.out.print("Enter Filename (without .csv): ");
            String csv = scan.nextLine();
            if (csv.equals("!")) {
                break;
            } else {
                int a = dataFrame.getDataFrameList().size();
                dataFrame.importCSV(csv);
                if (dataFrame.getDataFrameList().size() != a) {
                    b = false;
                }
            }
        }
    }

    public static void changeActiveCSV() {
        Scanner scan = new Scanner(System.in);
        boolean b = true;

        while (b) {
            System.out.print("Change dataframe (press '!' to exit): ");
            String choice = scan.nextLine();
            if (choice.equals("!")) {
                break;
            } else {
                File a = dataFrame.changeActiveCSV(choice);
                if (a == dataFrame.getFile()) {
                    b = false;
                }
            }
        }
    }

    public static void numericColumn(String choice) {
        Scanner scan = new Scanner(System.in);
        boolean b = true;

        while (b) {
            System.out.print("Enter column name (press '!' to exit): ");
            String columnName = scan.nextLine();

            if (columnName.equals("!")) {
                break;
            } else {
                double result = dataFrame.numericColumn(columnName, choice);
                if (choice.equals("a")) {
                    System.out.println(columnName + " average: " + result);
                } else if (choice.equals("m")) {
                    System.out.println(columnName + " minimum: " + result);
                } else if (choice.equals("x")) {
                    System.out.println(columnName + " maximum: " + result);
                } else {
                    if (result == 1) {
                        System.out.println(columnName + " freq table");
                        dataFrame.frequencyTable(dataFrame.minimumColumn(), dataFrame.maximumColumn());
                    }
                }

                if (result != 0) { // if it succeeded, the loop stoped
                    b = false;
                }

            }
        }
    }

}
