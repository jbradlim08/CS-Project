import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        DataFrame dataFrame = new DataFrame();
        File file = new File("Divvy_Trips_July2013.csv");

        a.add("1");
        a.add("2");
        a.add("3");
        a.add("4");
        a.add("5");
        a.add("6");
        a.add("7");

        System.out.println(a.indexOf("ada"));
        System.out.println(a);
        double sum = 0;
        List<String> columnData = dataFrame.getColumnDataFromColumn(file, 4);
        dataFrame.setColumnHeadersandDatatypes(file);

        for (int i = 2; i < columnData.size(); i++) {
            double value = Double.parseDouble(columnData.get(i));
            sum += value;
        }
        System.out.println("average: " + sum / a.size());

        System.out.println(dataFrame.getColumnDatatypes());
        System.out.println(dataFrame.getColumnDatatypes().get(1));
        System.out.println(!(dataFrame.getColumnDatatypes().get(1).equals("int")));

        File addfile = new File("kkdfd.csv");
        System.out.println(addfile);
    }
}
