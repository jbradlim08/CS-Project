import java.util.*;
import java.io.*;

public class DataFrame {

    private ArrayList<String> columnHeaders = new ArrayList<>();
    private ArrayList<String> columnDataTypes = new ArrayList<>();
    private File file;

    public DataFrame() {

    }

    public List<String> getColumnHeaders() {
        return Collections.unmodifiableList(columnHeaders);
    }

    public List<String> getColumnDataTypes() {
        return Collections.unmodifiableList(columnDataTypes);
    }

    public void setColumnHeaders() {
        try {

            Scanner scanFile = new Scanner(file);
            while (scanFile.hasNext()) {
                columnHeaders.add();
            }
        } catch (FileNotFoundException fne) {
            System.out.println(fne);
        }

    }

    public File importCSV() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter Filename (without .csv): ");
        String csv = scan.nextLine();
        String importcsv = csv + ".csv";

        File file = new File(importcsv);
        this.file = file;

        scan.close();

        return file;
    }

}
