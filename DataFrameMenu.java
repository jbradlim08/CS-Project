import java.util.*;

public class DataFrameMenu {

    public static void main(String[] args) {
        DataFrame dataFrame = new DataFrame();
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
                    System.out.println("DataFrame Available: " + dataFrame.getFile().getName());
                    System.out.println("Active: " + dataFrame.getFile().getName() + " " + dataFrame.getColumnHeaders()
                            + " " + dataFrame.getColumnDatatypes());
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
                            dataFrame.importCSV();
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
                            dataFrame.importCSV();
                            break;
                        case "c":
                            dataFrame.changeActiveCSV();
                            break;
                        case "a":
                            dataFrame.averageColumn();
                            break;
                        case "m":
                            dataFrame.minimumColumn();
                            break;
                        case "x":
                            dataFrame.maximumColumn();
                            break;
                        case "f":
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
        }
        scan.close();
    }
}
