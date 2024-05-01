import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        DataFrame dataFrame = new DataFrame();
        File file = new File("Divvy_Trips_July2013.csv");
        System.out.println("pass");

        String newFile = "output.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));) {
            Scanner scan = new Scanner(file);

            bw.write(scan.nextLine() + "\n");
            bw.write(scan.nextLine() + "\n");
            bw.write(scan.nextLine() + "\n");

            File nFile = new File(newFile);
            Scanner scanner = new Scanner(nFile);

            bw.flush();
            bw.close();
            if (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            } else {
                System.out.println("file is empty");
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

    }

}
