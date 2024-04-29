import java.util.*;
import java.io.*;
import java.nio.file.NoSuchFileException;

public class DataFrame {

    private ArrayList<String> columnHeaders = new ArrayList<>();
    private ArrayList<String> columnDataTypes = new ArrayList<>();
    private ArrayList<String> columnData = new ArrayList<>();
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

    public void setColumnHeadersandDatatypes(File file) {
        columnHeaders.clear();
        columnDataTypes.clear();
        try (Scanner scanFile = new Scanner(file)) {
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
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("An error occurred while reading the file");
        }

    }

    public File getFile() {
        return this.file;
    }

    public ArrayList<String> getColumnDataFromColumn(File file, int columnIndex) {
        columnData.clear();
        try (Scanner scanFile = new Scanner(file)) {
            while (scanFile.hasNextLine()) { // check all line
                String line = scanFile.nextLine();
                String[] data = line.split(",");

                // Check if the line contains the desired column index
                if (data.length > columnIndex) {
                    // Get data from the column index
                    String value = data[columnIndex].trim(); // remove the space
                    columnData.add(value);
                } else {
                    System.out.println("Column index " + columnIndex + " out of bounds in line: " + line);
                }
            }
        } catch (FileNotFoundException fne) {
            System.out.println("File not found");
        } catch (Exception e) {
            System.out.println("An error occurred while reading the file");
        }

        return columnData;
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

                setColumnHeadersandDatatypes(file);
            } catch (FileNotFoundException fne) {
                System.out.println("no such file");
                System.out.print("Enter Filename (without .csv): ");
            }
        }

    }

    public void changeActiveCSV() {

    }

    public void averageColumn() {
        Scanner scan = new Scanner(System.in);

        boolean b = true;
        while (b) {
            try {

                System.out.print("Enter column name (press '!' to exit): ");
                String columnName = scan.nextLine();
                if (columnName.equals("!")) {
                    break;
                }
                int index = columnHeaders.indexOf(columnName); // get the index

                if (index == -1) {
                    System.out.println("...no such option...");
                    averageColumn();
                    break;
                } else if (!(columnDataTypes.get(index).equals("int"))
                        && !(columnDataTypes.get(index).equals("double"))) {
                    System.out.println("only numeric value");
                    averageColumn();
                    break;
                } else {
                    getColumnDataFromColumn(file, index); // update and get columnData

                    double sum = 0;
                    if (columnData.size() > 0) {
                        for (int i = 2; i < columnData.size(); i++) {
                            sum += Double.parseDouble(columnData.get(i));
                        }
                        System.out.println(columnName + " average: " + (sum / columnData.size()));
                        break;
                    } else {
                        System.out.println("no data in current column");
                    }
                    b = false;
                    break;
                }
            } catch (NumberFormatException nfe) {
                System.out.println(nfe);
            }
        }
        scan.close();
    }

    public void minimumColumn() {
        Scanner scan = new Scanner(System.in);

        boolean b = true;
        while (b) {
            try {

                System.out.print("Enter column name (press '!' to exit): ");
                String columnName = scan.nextLine();
                if (columnName.equals("!")) {
                    break;
                }
                int index = columnHeaders.indexOf(columnName); // get the index

                if (index == -1) {
                    System.out.println("...no such option...");
                    minimumColumn();
                    break;
                } else if (!(columnDataTypes.get(index).equals("int"))
                        && !(columnDataTypes.get(index).equals("double"))) {
                    System.out.println("only numeric value");
                    minimumColumn();
                    break;
                } else {
                    getColumnDataFromColumn(file, index); // update and get columnData

                    if (columnData.size() > 0) {
                        double min = Double.parseDouble(columnData.get(2));
                        for (int i = 2; i < columnData.size(); i++) {
                            if (min > Double.parseDouble(columnData.get(i))) {
                                min = Double.parseDouble(columnData.get(i));
                            }
                        }
                        System.out.println(columnName + " minimum: " + min);
                    } else {
                        System.out.println("no data in current column");
                    }
                    b = false;
                }
            } catch (NumberFormatException nfe) {
                System.out.println(nfe);
            }
        }
        scan.close();
    }

    public void maximumColumn() {
        Scanner scan = new Scanner(System.in);

        boolean b = true;
        while (b) {
            try {

                System.out.print("Enter column name (press '!' to exit): ");
                String columnName = scan.nextLine();
                if (columnName.equals("!")) {
                    break;
                }
                int index = columnHeaders.indexOf(columnName); // get the index

                if (index == -1) {
                    System.out.println("...no such option...");
                    maximumColumn();
                    break;
                } else if (!(columnDataTypes.get(index).equals("int"))
                        && !(columnDataTypes.get(index).equals("double"))) {
                    System.out.println("only numeric value");
                    maximumColumn();
                    break;
                } else {
                    getColumnDataFromColumn(file, index); // update and get columnData

                    if (columnData.size() > 0) {
                        double max = Double.parseDouble(columnData.get(2));
                        for (int i = 2; i < columnData.size(); i++) {
                            if (max < Double.parseDouble(columnData.get(i))) {
                                max = Double.parseDouble(columnData.get(i));
                            }
                        }
                        System.out.println(columnName + " minimum: " + max);
                    } else {
                        System.out.println("no data in current column");
                    }
                    b = false;
                }
            } catch (NumberFormatException nfe) {
                System.out.println(nfe);
            }
        }
        scan.close();
    }

    public List<Double> frequencyTable(String columnName) {

    }

    public List<String> subsetDataFrame(String conditions) {

    }

    public File exportToCSV(String fileName) {

    }
}
