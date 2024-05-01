import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        DataFrame dataFrame = new DataFrame();
        File file = new File("Divvy_Trips_July2013.csv");
        System.out.println("pass");

        try {
            Scanner scanFile = new Scanner(file);
            System.out.println("pass");
            System.out.println(scanFile.nextLine());
            int i = 0;
            while (i < 5) {
                System.out.println(scanFile.nextLine());
                i++;
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe);
        }
        String newFile = "output.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));) {
            Scanner scan = new Scanner(file);

            bw.write(scan.nextLine() + "\n");
            bw.write(scan.nextLine() + "\n");
            bw.write(scan.nextLine() + "\n");

        } catch (IOException ioe) {
            System.out.println(ioe);
        }

    }

}
