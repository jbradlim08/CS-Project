import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();

        a.add("1");
        a.add("2");
        a.add("3");
        a.add("4");
        a.add("5");
        a.add("6");
        a.add("7");

        System.out.println(a.indexOf("c"));
        System.out.println(a);
        System.out.println(Double.parseDouble(a.get(3)));

    }
}
