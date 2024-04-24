import java.util.*;
import java.io.*;
import java.nio.file.NoSuchFileException;

public class DataFrame {

    private ArrayList<String> columnHeaders = new ArrayList<>();
    private ArrayList<String> columnDataTypes = new ArrayList<>();
    private File file = null;
    private BufferedReader br;

    public DataFrame() {

    }

    public List<String> getColumnHeaders() {
        return this.columnHeaders;
    }

    public List<String> getColumnDatatypes() {
        return this.columnDataTypes;
    }

    public void setColumnHeadersandDatatypes() {
        try (Scanner scanFile = new Scanner(this.file)) {
            if (scanFile.hasNextLine()) {
                String firstLine = scanFile.nextLine();
                String[] headers = firstLine.split(",");

                // Add the headers to the list
                for (String header : headers) {
                    columnHeaders.add(header);
                }

                if (scanFile.hasNextLine()) {
                    String secondLine = scanFile.nextLine();
                    String[] datatypes = secondLine.split(",");

                    for (String datatype : datatypes) {
                        columnDataTypes.add(datatype);
                    }
                }
            } else {
                System.out.println("File is empty.");
            }
        } catch (FileNotFoundException fne) {
            System.out.println("File not found: ");
        } catch (Exception e) {
            System.out.println("An error occurred while reading the file");
        }

    }

    public File getFile() {
        return this.file;
    }

    public void importCSV() {

        Scanner scan = new Scanner(System.in);
        boolean b = true;
        System.out.print("Enter Filename (without .csv): ");
        while (b) {
            try {

                String csv = scan.nextLine(); // try input
                String importcsv = csv + ".csv";

                file = new File(importcsv);
                br = new BufferedReader(new FileReader(file));
                b = false;

                setColumnHeadersandDatatypes();
            } catch (FileNotFoundException fne) {
                System.out.println("no such file");
                System.out.print("Enter Filename (without .csv): ");
            }
        }

    }

    public void changeActiveCSV() {

    }

    public double averageColumn() {
        boolean b = true;
        while (b) {
            try {
                System.out.println("Enter column name:");

                Scanner scan = new Scanner(System.in);
                String input = scan.nextLine();

            } catch (InputMismatchException ime) {

            }
        }

    }

    public double minimumColumn() {
        return;
    }

    public double maximumColumn() {

    }

    public List<Double> frequencyTable(String columnName) {

    }

    public List<String> subsetDataFrame(String conditions) {

    }

    public File exportToCSV(String fileName) {

    }
}
