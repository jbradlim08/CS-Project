import java.util.*;
import java.io.*;

public class DataFrame {

    private ArrayList<String> columnHeaders = new ArrayList<>();
    private ArrayList<String> columnDataTypes = new ArrayList<>();
    private ArrayList<String> columnData = new ArrayList<>();
    private int rowsCount;

    // list of available dataframe
    private ArrayList<File> dataFrameList = new ArrayList<>();

    private File activeFile = null;
    private BufferedReader br;

    public DataFrame() {

    }

    public File getFile() {
        return this.activeFile;
    }

    public List<String> getColumnHeaders() {
        return this.columnHeaders;
    }

    public List<String> getColumnDatatypes() {
        return this.columnDataTypes;
    }

    public List<File> getDataFrameList() {
        return this.dataFrameList;
    }

    public int getRowsCount() {
        rowsCount = 0;
        rowsCount();
        return this.rowsCount - 2; // '-2' minus the the header and datatype
    }

    public void rowsCount() {
        try {
            Scanner scanFile = new Scanner(activeFile);
            boolean b = true;

            while (b) {
                if (scanFile.hasNextLine()) {
                    scanFile.nextLine(); // read the next line
                    this.rowsCount++;
                } else {
                    b = false;
                }
            }
        } catch (FileNotFoundException fne) {
            System.out.println("File not found");
        }
    }

    public String removeFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');

        if (lastDotIndex > 0) {
            return fileName.substring(0, lastDotIndex);
        }

        return fileName; // If no '.' or '.' is the first character, return the original filename
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

                String csv = scan.nextLine();
                String importcsv = csv + ".csv";

                activeFile = new File(importcsv);
                br = new BufferedReader(new FileReader(activeFile)); // read the file and catching fne

                dataFrameList.add(activeFile); // add the new dataFrame
                setColumnHeadersandDatatypes(activeFile);

                b = false; // stop the loop
            } catch (FileNotFoundException fne) {
                System.out.println("no such file");
                System.out.print("Enter Filename (without .csv): ");
            }
        }

    }

    public void changeActiveCSV() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Change dataframe (press '!' to exit): ");
        boolean b = true;

        while (b) {
            String choice = scan.nextLine();
            if (choice.equals("!")) {
                break;
            }
            File selectedFile = new File(choice + ".csv");
            if (dataFrameList.contains(selectedFile)) {
                activeFile = selectedFile;
                setColumnHeadersandDatatypes(activeFile);
                b = false;
            } else {
                changeActiveCSV();
                break;
            }
        }
    }

    public void numericCalculationColumn(String choice) {
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
                    numericCalculationColumn(choice);
                    break;
                } else if (!(columnDataTypes.get(index).equals("int"))
                        && !(columnDataTypes.get(index).equals("double"))) {
                    System.out.println("only numeric value");
                    numericCalculationColumn(choice);
                    break;
                } else {
                    getColumnDataFromColumn(activeFile, index); // update and get columnData
                    if (columnData.size() > 0) {
                        if (choice.equals("a")) {
                            System.out.println(columnName + " average: " + averageColumn());
                        } else if (choice.equals("m")) {
                            System.out.println(columnName + " minimum: " + minimumColumn());
                        } else {
                            System.out.println(columnName + " maximum: " + maximumColumn());
                        }
                        break;
                    } else {
                        System.out.println("no data in current column");
                        break;
                    }
                }
            } catch (NumberFormatException nfe) {
                System.out.println(nfe);
            }
        }
    }

    public double averageColumn() {
        double sum = 0;
        // starts from the data value at index 2
        for (int i = 2; i < columnData.size(); i++) {
            sum += Double.parseDouble(columnData.get(i));
        }

        return sum / columnData.size();
    }

    public double minimumColumn() {
        double min = Double.parseDouble(columnData.get(2));
        for (int i = 2; i < columnData.size(); i++) {
            if (min > Double.parseDouble(columnData.get(i))) {
                min = Double.parseDouble(columnData.get(i));
            }
        }

        return min;
    }

    public double maximumColumn() {
        double max = Double.parseDouble(columnData.get(2));
        for (int i = 2; i < columnData.size(); i++) {
            if (max < Double.parseDouble(columnData.get(i))) {
                max = Double.parseDouble(columnData.get(i));
            }
        }

        return max;
    }
    /*
     * public void averageColumn() {
     * Scanner scan = new Scanner(System.in);
     * 
     * boolean b = true;
     * while (b) {
     * try {
     * 
     * System.out.print("Enter column name (press '!' to exit): ");
     * String columnName = scan.nextLine();
     * if (columnName.equals("!")) {
     * break;
     * }
     * int index = columnHeaders.indexOf(columnName); // get the index
     * 
     * if (index == -1) {
     * System.out.println("...no such option...");
     * averageColumn();
     * break;
     * } else if (!(columnDataTypes.get(index).equals("int"))
     * && !(columnDataTypes.get(index).equals("double"))) {
     * System.out.println("only numeric value");
     * averageColumn();
     * break;
     * } else {
     * getColumnDataFromColumn(file, index); // update and get columnData
     * 
     * double sum = 0;
     * if (columnData.size() > 0) {
     * for (int i = 2; i < columnData.size(); i++) {
     * sum += Double.parseDouble(columnData.get(i));
     * }
     * System.out.println(columnName + " average: " + (sum / columnData.size()));
     * break;
     * } else {
     * System.out.println("no data in current column");
     * }
     * b = false;
     * break;
     * }
     * } catch (NumberFormatException nfe) {
     * System.out.println(nfe);
     * }
     * }
     * }
     * 
     * public void minimumColumn() {
     * Scanner scan = new Scanner(System.in);
     * 
     * boolean b = true;
     * while (b) {
     * try {
     * 
     * System.out.print("Enter column name (press '!' to exit): ");
     * String columnName = scan.nextLine();
     * if (columnName.equals("!")) {
     * break;
     * }
     * int index = columnHeaders.indexOf(columnName); // get the index
     * 
     * if (index == -1) {
     * System.out.println("...no such option...");
     * minimumColumn();
     * break;
     * } else if (!(columnDataTypes.get(index).equals("int"))
     * && !(columnDataTypes.get(index).equals("double"))) {
     * System.out.println("only numeric value");
     * minimumColumn();
     * break;
     * } else {
     * getColumnDataFromColumn(file, index); // update and get columnData
     * 
     * if (columnData.size() > 0) {
     * double min = Double.parseDouble(columnData.get(2));
     * for (int i = 2; i < columnData.size(); i++) {
     * if (min > Double.parseDouble(columnData.get(i))) {
     * min = Double.parseDouble(columnData.get(i));
     * }
     * }
     * System.out.println(columnName + " minimum: " + min);
     * } else {
     * System.out.println("no data in current column");
     * }
     * b = false;
     * }
     * } catch (NumberFormatException nfe) {
     * System.out.println(nfe);
     * }
     * }
     * }
     * 
     * public void maximumColumn() {
     * Scanner scan = new Scanner(System.in);
     * 
     * boolean b = true;
     * while (b) {
     * try {
     * 
     * System.out.print("Enter column name (press '!' to exit): ");
     * String columnName = scan.nextLine();
     * if (columnName.equals("!")) {
     * break;
     * }
     * int index = columnHeaders.indexOf(columnName); // get the index
     * 
     * if (index == -1) {
     * System.out.println("...no such option...");
     * maximumColumn();
     * break;
     * } else if (!(columnDataTypes.get(index).equals("int"))
     * && !(columnDataTypes.get(index).equals("double"))) {
     * System.out.println("only numeric value");
     * maximumColumn();
     * break;
     * } else {
     * getColumnDataFromColumn(file, index); // update and get columnData
     * 
     * if (columnData.size() > 0) {
     * double max = Double.parseDouble(columnData.get(2));
     * for (int i = 2; i < columnData.size(); i++) {
     * if (max < Double.parseDouble(columnData.get(i))) {
     * max = Double.parseDouble(columnData.get(i));
     * }
     * }
     * System.out.println(columnName + " minimum: " + max);
     * } else {
     * System.out.println("no data in current column");
     * }
     * b = false;
     * }
     * } catch (NumberFormatException nfe) {
     * System.out.println(nfe);
     * }
     * }
     * }
     */

    public List<Double> frequencyTable(String columnName) {

    }

    public List<String> subsetDataFrame(String conditions) {

    }

    public File exportToCSV(String fileName) {

    }
}
