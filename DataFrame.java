import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.DosFileAttributeView;

public class DataFrame {

    private List<String> columnHeaders = new ArrayList<>();
    private List<String> columnDataTypes = new ArrayList<>();
    private List<String> columnData = new ArrayList<>();
    private int rowsCount;
    private static final int DEFAULT_FREQUENCY = 5;

    // list of available dataframe
    private List<File> dataFrameList = new ArrayList<>();

    private File activeFile = null;
    private BufferedReader br;

    // garbage
    private List<String> log = new ArrayList<>();

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

    public List<String> getLog() {
        return log;
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
            log.add("File not found while trying to count the rows");
            printToLog();
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
                System.out.println("...File is empty...");
                printToLog();
            }
        } catch (FileNotFoundException fne) {
            System.out.println("...File not found...");
            log.add("File not found while trying to set the column headers and datatype");
            printToLog();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.add(e.getMessage());
            printToLog();
        }

    }

    public List<String> getColumnDataFromColumn(int columnIndex) {
        columnData.clear();
        try (Scanner scanFile = new Scanner(activeFile)) {
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
            log.add("File not found while trying to get the columndata");
            printToLog();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.add(e.getMessage());
        }

        return columnData;
    }

    public List<File> importCSV(String choice) {
        try {
            String importcsv = choice + ".csv";

            activeFile = new File(importcsv);
            br = new BufferedReader(new FileReader(activeFile)); // read the file and catching fne

            if (!(dataFrameList.contains(activeFile))) {
                dataFrameList.add(activeFile); // add the new dataFrame
                setColumnHeadersandDatatypes(activeFile);
            } else {
                System.out.println("...File is exist...");
                log.add("File is already exist while trying to import a new CSV");
                printToLog();
            }

        } catch (FileNotFoundException fne) {
            System.out.println("...File not found...");
            log.add("File not found while trying to import the CSV");
            printToLog();
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
            log.add("File not found while trying to change the CSV");
            printToLog();
            return new File("debug.csv"); // to continue the loop by returning debug file
        }
        return activeFile;

    }

    public double numericColumn(String columnName, String choice) {
        try {
            int index = columnHeaders.indexOf(columnName); // get the index

            if (index == -1) {
                System.out.println("...no such option...");
                log.add("No option available while trying to access the columnHeaders");
                printToLog();

            } else if (!(columnDataTypes.get(index).equals("int"))
                    && !(columnDataTypes.get(index).equals("double"))) {
                System.out.println("only numeric value");
                log.add("Trying to access the non-numeric value");
                printToLog();

            } else {
                getColumnDataFromColumn(index); // update and get columnData
                if (columnData.size() > 2) {
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
                    log.add("No data available in current column");
                    printToLog();
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

        return sum / (columnData.size() - 2);
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
            log.add(nfe.getMessage());
            printToLog();
        }
    }

    private String frequencyString(double first, double second, int index) {
        if (index == DEFAULT_FREQUENCY - 1) {
            return "[" + first + ", " + second + "]" + "\n"; // if it is at the end print "]"
        } else {
            return "[" + first + ", " + second + ") ";
        }
    }

    public boolean subsetDataFrame(String input) {
        String[] validOperators = { "==", "less-than", "greater-than", "!=" };
        String columnName = "";
        String operator = "";
        String value = "";
        String[] parts = input.split("\\s+"); // any whitespace

        // stop the code if the length is not 3
        if (parts.length == 3) {
            columnName = parts[0];
            if (parts[1].equals("<")) // because file can't contain > or <
                operator = "less-than";
            else if (parts[1].equals(">"))
                operator = "greater-than";
            else {
                operator = parts[1];
            }
            value = parts[2];
        } else {
            System.out.println("...invalid input...");
            log.add("Invalid input detected while trying to subset the active DataFrame");
            printToLog();
            return false;
        }

        // stop the code if the input does not contain the provided value
        if (!(columnHeaders.contains(columnName))) {
            System.out.println("...no such option...");
            log.add("No option available while trying to subset the active DataFrame");
            printToLog();
        } else if (!(Arrays.asList(validOperators).contains(operator))) {
            System.out.println("...Invalid Operator...");
            log.add("Invalid Operator detected while trying to subset the active DataFrame");
            printToLog();
        } else {
            String newFile = removeFileExtension(activeFile.getName()) + "(" + columnName + " " + operator + " " + value
                    + ").csv";

            String folderpath = "backupCSV/"; // setting the location of the filewriter
            String filePath = folderpath + newFile;

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

                Scanner scanFile = new Scanner(activeFile);
                bw.write(scanFile.nextLine() + "\n"); // get the header
                bw.write(scanFile.nextLine() + "\n"); // get the datatype

                int index = columnHeaders.indexOf(columnName); // get the index of columnName
                getColumnDataFromColumn(index); // get the current columnData

                // if it is number
                if (columnDataTypes.get(index).equals("int") || columnDataTypes.get(index).equals("double")) {
                    double val = Double.parseDouble(value); // throw exception if wrong

                    for (int i = 2; i < columnData.size(); i++) {
                        String line = scanFile.nextLine();
                        double rowValue = Double.parseDouble(columnData.get(i));
                        boolean meetsCondition = true;

                        switch (operator) {
                            case "==":
                                meetsCondition = (rowValue == val);
                                break;
                            case "less-than":
                                meetsCondition = (rowValue < val);
                                break;
                            case "greater-than":
                                meetsCondition = (rowValue > val);
                                break;
                            case "!=":
                                meetsCondition = (rowValue != val);
                                break;
                            default:
                                System.out.println("...Invalid operator...");
                                log.add("Invalid Operator detected while trying to subset the active DataFrame");
                                printToLog();
                                bw.flush();
                                bw.close();
                                new File(filePath).delete(); // delete the file if fails
                                return false;
                        }

                        // Write row to output file if it meets the condition
                        if (meetsCondition) {
                            bw.write(line + "\n");
                        }
                    }

                } else {
                    String val = value;
                    if (columnData.contains(val) && operator.equals("==")) {
                        for (int i = 2; i < columnData.size(); i++) {
                            String line = scanFile.nextLine();
                            String rowValue = columnData.get(i);

                            if (val.equals(rowValue)) {
                                bw.write(line + "\n");
                            }
                        }
                    } else {
                        System.out.println("...Invalid Operator for String or there are no data left...");
                        log.add(
                                "Invalid Operator or no data left detected while trying to subset the active DataFrame");
                        printToLog();
                        bw.flush();
                        bw.close();
                        new File(filePath).delete(); // delete the file if fails
                        return false;
                    }

                }
                bw.flush();
                bw.close();
                File file = new File(filePath);
                BufferedReader br = new BufferedReader(new FileReader(file));
                importCSV(removeFileExtension(file + "")); // add to available dataframe and change the active file

                return true;
            } catch (NumberFormatException nfe) {
                System.out.println("...input is NaN...");
                log.add("Input is NOT a number while subsetting the numeric data");
                printToLog();

                new File(filePath).delete();
                return false;
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                log.add(ioe.getMessage());
                printToLog();

                new File(filePath).delete();
                return false;
            }

        }
        return false;
    }

    public void exportToCSV(File file) {
        try {
            File filePath = new File(activeFile.getName());
            if (!(filePath.exists())) {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)); // it will overwrite the previous
                                                                                  // log.txt
                Scanner scan = new Scanner(activeFile);

                while (scan.hasNextLine()) {
                    bw.write(scan.nextLine() + "\n");
                }
                bw.flush();
                bw.close();
            } else {
                System.out.println("...File is exist...");
                log.add("File is already exist while trying to export the CSV");
                printToLog();
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            log.add(ioe.getMessage());
            printToLog();
        }
    }

    public void clearFolder() {
        File folder = new File("backupCSV/");

        // delete all the backup files
        if (folder.exists()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file != null) {

                    file.delete();
                }
            }
        } else {
            System.out.println("...Folder not exist...");
            log.add("Folder is not exist while trying to delete the file in the folder");
            printToLog();
        }
    }

    public void printToLog() {
        try {
            File file = new File("log.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file)); // it will overwrite the previous log.txt
            for (String a : log) {
                bw.write(a + "\n");
            }
            bw.flush();
            bw.close();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
