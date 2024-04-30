import java.util.*;
import java.io.*;

public class DataFrame {

    private ArrayList<String> columnHeaders = new ArrayList<>();
    private ArrayList<String> columnDataTypes = new ArrayList<>();
    private ArrayList<String> columnData = new ArrayList<>();
    private int rowsCount;
    private static final int DEFAULT_FREQUENCY = 5;

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
        rowsCount();
        return this.rowsCount - 2; // '-2' minus the the header and datatype
    }

    public void rowsCount() {
        rowsCount = 0;
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
            System.out.println("...File not found...");
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
                System.out.println("File is empty");
            }
        } catch (FileNotFoundException fne) {
            System.out.println("...File not found...");
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
            System.out.println("...File not found...");
        } catch (Exception e) {
            System.out.println("An error occurred while reading the file");
        }

        return columnData;
    }

    public ArrayList<File> importCSV(String choice) {
        try {
            String importcsv = choice + ".csv";

            activeFile = new File(importcsv);
            br = new BufferedReader(new FileReader(activeFile)); // read the file and catching fne

            if (!(dataFrameList.contains(activeFile))) {
                dataFrameList.add(activeFile); // add the new dataFrame
                setColumnHeadersandDatatypes(activeFile);
            } else {
                System.out.println("...File is exist");
            }

        } catch (FileNotFoundException fne) {
            System.out.println("...File not found...");
        }
        return dataFrameList;
    }

    public File changeActiveCSV(String choice) {

        File selectedFile = new File(choice + ".csv");
        if (dataFrameList.contains(selectedFile)) {
            activeFile = selectedFile;
            setColumnHeadersandDatatypes(activeFile);
        } else {
            System.out.println("...File not found...");
        }
        return activeFile;

    }

    public double numericColumn(String columnName, String choice) {
        try {
            int index = columnHeaders.indexOf(columnName); // get the index

            if (index == -1) {
                System.out.println("...no such option...");

            } else if (!(columnDataTypes.get(index).equals("int"))
                    && !(columnDataTypes.get(index).equals("double"))) {
                System.out.println("only numeric value");

            } else {
                getColumnDataFromColumn(activeFile, index); // update and get columnData
                if (columnData.size() > 0) {
                    if (choice.equals("a")) {
                        return averageColumn();
                    } else if (choice.equals("m")) {
                        return minimumColumn();
                    } else if (choice.equals("x")) {
                        return maximumColumn();
                    } else {
                        return 1; // to return frequency
                    }
                } else {
                    System.out.println("no data in current column");
                }
            }
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }
        return 0;
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

    public void frequencyTable(double min, double max) {
        double gap = (max - min) / DEFAULT_FREQUENCY;
        int count = 0;
        ArrayList<Integer> amount = new ArrayList<>();

        try {
            // print the table frequency
            for (int i = 0; i < DEFAULT_FREQUENCY; i++) {
                count = 0; // initial condition
                max = min + gap; // initial condition
                System.out.print(frequencyString(min, max, i));

                // the amount
                for (int j = 2; j < columnData.size(); j++) {
                    double currentNumber = Double.parseDouble(columnData.get(j));
                    if (i != DEFAULT_FREQUENCY - 1) {
                        if (currentNumber >= min && currentNumber < max) { // excluded the max
                            count++;
                        }
                    } else {
                        if (currentNumber >= min && currentNumber <= max) { // included the max
                            count++;
                        }
                    }
                }
                amount.add(count);

                min += gap; // update min gap
            }

            // print the amount
            for (int i : amount) {
                System.out.print(i + " ");
            }
            System.out.println(" ");
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        }
    }

    private String frequencyString(double first, double second, int index) {
        if (index == DEFAULT_FREQUENCY - 1) {
            return "[" + first + ", " + second + "]" + "\n"; // if it is at the end print "]"
        } else {
            return "[" + first + ", " + second + ") ";
        }
    }

    public void subsetDataFrame() {
        final String[] validOperator = { "==", "<", ">", "!=" };
        Scanner scan = new Scanner(System.in);
        double bound = 0; // for type: numbers
        String x = "";
        boolean b = true;

        try {
            while (b) {

                System.out.print("Enter column name operator(== < > !=) value (ex: ID < 1000) (press '!' to exit): ");
                String input = scan.nextLine();
                String[] d = input.split(" ");
                int index = columnHeaders.indexOf(d[1]);
                if (index == -1 || d.length > 3 || !(columnHeaders.contains(d[0]))
                        || !(Arrays.asList(validOperator).contains(d[1]))) {
                    System.out.println("An error occurred");
                    subsetDataFrame();
                    break;
                } else {
                    getColumnDataFromColumn(activeFile, index);
                    if (columnDataTypes.get(index).equals("int") || columnDataTypes.get(index).equals("double")) {
                        bound = Double.parseDouble(d[2]);
                        // throw exception if the bound is not number
                    } else {
                        if (columnData.contains(x)) {
                            x = d[2];
                        }
                        dataFrameList.add(new File(activeFile + " (" + d[0] + " " + d[1] + " " + d[2] + ")"));

                    }
                    b = false;
                }
            }

        } catch (NumberFormatException nfe) {
            System.out.println("...input is NaN...");
        }

    }

    public File exportToCSV(String fileName) {

    }
}
