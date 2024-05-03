import java.util.*;
import java.io.*;

public class DataFrameMenu {

    static DataFrame dataFrame = new DataFrame();

    public static void main(String[] args) {
        // initial condition
        dataFrame.clearFolder(); // clear all backup folder
        dataFrame.getLog().clear();
        dataFrame.printToLog();

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
                        sum += dataFrame.removeFileExtension(s.getName() + "") + " "; // make the file to String without
                                                                                      // csv
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
                    System.out.println("z: export the active DataFrame to CSV");
                    System.out.println("q: Quit");
                }

                String choice = scan.nextLine();

                if (dataFrame.getFile() == null) {
                    switch (choice) {
                        case "i":
                            importCSV();
                            break;
                        case "q":
                            dataFrame.printToLog();
                            loop = false;
                            break;
                        default:
                            System.out.println("...no such option...");
                            dataFrame.getLog().add("No available option while trying to access the method");
                            dataFrame.printToLog();
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
                            subsetDataFrame(choice);
                            break;
                        case "z":
                            dataFrame.exportToCSV(dataFrame.getFile());
                            break;
                        case "q":
                            dataFrame.printToLog();
                            loop = false;
                            break;
                        default:
                            System.out.println("...no such option...");
                            dataFrame.getLog().add("No available option while trying to access the method");
                            dataFrame.printToLog();

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
        while (true) {
            System.out.print("Enter Filename (without .csv) (press '!' to exit): ");
            String csv = scan.nextLine();
            if (csv.equals("!")) { // to exit
                break;
            } else {
                int temp = dataFrame.getDataFrameList().size();
                dataFrame.importCSV(csv);
                if (dataFrame.getDataFrameList().size() != temp) {
                    break;
                }
            }
        }
    }

    public static void changeActiveCSV() {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("Change dataframe (press '!' to exit): ");
            String choice = scan.nextLine();
            if (choice.equals("!")) { // to exit
                break;
            } else {
                String temp = dataFrame.changeActiveCSV(choice).getName();
                if (!(temp.equals("debug.csv"))) {
                    break;
                }
            }
        }
    }

    public static void numericColumn(String choice) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("Enter column name (press '!' to exit): ");
            String columnName = scan.nextLine();

            if (columnName.equals("!")) { // to exit
                break;
            } else {
                double result = dataFrame.numericColumn(columnName, choice);
                if (result != 0) {

                    if (choice.equals("a")) {
                        System.out.println(columnName + " average: " + result);
                    } else if (choice.equals("m")) {
                        System.out.println(columnName + " minimum: " + result);
                    } else if (choice.equals("x")) {
                        System.out.println(columnName + " maximum: " + result);
                    } else if (result == 1) { // frequency check
                        System.out.println(columnName + " freq table");
                        dataFrame.frequencyTable(dataFrame.minimumColumn(), dataFrame.maximumColumn());

                    }
                    break; // if it succeeded, the loop stoped
                }
            }
        }
    }

    public static void subsetDataFrame(String choice) {
        Scanner scan = new Scanner(System.in);

        while (true) {

            System.out.print("Enter column name operator(== < > !=) value (ex: ID < 1000) (press '!' to exit): ");
            String input = scan.nextLine();
            if (input.equals("!")) { // to exit
                break;
            } else {
                if (dataFrame.subsetDataFrame(input) == true) {
                    break;
                }
            }
        }
    }
}
