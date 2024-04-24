import java.util.*;
import java.io.*;
import java.nio.file.NoSuchFileException;

public class DataFrame {

    private ArrayList<String> columnHeaders = new ArrayList<>();
    private ArrayList<String> columnDataTypes = new ArrayList<>();
    private BufferedReader file = null;

    public DataFrame() {

    }

    public List<String> getColumnHeaders() {
        return Collections.unmodifiableList(columnHeaders);
    }

    public List<String> getColumnDataTypes() {
        return Collections.unmodifiableList(columnDataTypes);
    }

    public BufferedReader getFile() {
        return this.file;
    }

    public void setColumnHeaders() {
        try {
            Scanner scanFile = new Scanner(file);
            while (scanFile.hasNext()) {

            }
        } catch (FileNotFoundException fne) {
            System.out.println(fne);
        }

    }

    public void importCSV() {

        Scanner scan = new Scanner(System.in);
        try {
            System.out.print("Enter Filename (without .csv): ");
            String csv = scan.nextLine();
            String importcsv = csv + ".csv";

            file = new BufferedReader(new FileReader(importcsv));

        } catch (FileNotFoundException fne) {
            System.out.println("no such file");
        }

    }

    public static double averageColumn(){
        try{    
            averageColumn();
        }
        catch(){

        }
    }

}
