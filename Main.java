import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        DataFrame dataFrame = new DataFrame();
        File file = new File("Divvy_Trips_July2013.csv");

        try {
            Scanner scanFile = new Scanner(file);
            System.out.println(scanFile.nextLine());
            int i = 0;
            while (i < 5) {
                System.out.println(scanFile.nextLine());
                i++;
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        }
    }

}
