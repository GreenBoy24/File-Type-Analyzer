package analyzer;

import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {
        String algorithm = args[0];
        String file = args[1];
        String pattern = args[2];
        String result = args[3];
        if(algorithm == "--naive"){
            naive(file, pattern, result);
        } else if("--KMP"){
            KMPSearch(file, pattern, result);
        } else{
            System.out.println("Wrong input.");
        }
    }

    private static void naive(String file, String pattern, String result){
        try {
            byte[] allBytes = Files.readAllBytes(Paths.get(file));
            String str = "";
            for (int i = 0; i < allBytes.length; i++) {
                str += (char) allBytes[i];
            }
            if (str.contains(pattern)) {
                System.out.println(result);
                System.out.println("It took " + System.nanoTime() + " nanoseconds");
            } else {
                System.out.println("Unknown file type");
                System.out.println("It took " + System.nanoTime() + " nanoseconds");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void KMPSearch(String file, String pattern, String result){
        int[] prefixFunc = prefixFunction(pattern);
        boolean answer = false;
        //ArrayList<Integer> occurrences = new ArrayList<Integer>();
        byte[] allBytes;
        String text = "";
        try {
            allBytes = Files.readAllBytes(Paths.get(file));
            for (int i = 0; i < allBytes.length; i++) {
                text += (char) allBytes[i];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            while (j > 0 && text.charAt(i) != pattern.charAt(j)) {
                j = prefixFunc[j - 1];
            }
            if (text.charAt(i) == pattern.charAt(j)) {
                j += 1;
            }
            if (j == pattern.length()) {
                //occurrences.add(i - j + 1);
                j = prefixFunc[j - 1];
                answer = true;
                break;
            }
        }

        if(answer){
            System.out.println(result);
            System.out.println("It took " + System.nanoTime() + " nanoseconds");
        } else{
            System.out.println("Unknown file type");
            System.out.println("It took " + System.nanoTime() + " nanoseconds");
        }
    }

    public static int[] prefixFunction(String str) {
        int[] prefixFunc = new int[str.length()];

        for (int i = 1; i < str.length(); i++) {
            int j = prefixFunc[i - 1];

            while (j > 0 && str.charAt(i) != str.charAt(j)) {
                j = prefixFunc[j - 1];
            }

            if (str.charAt(i) == str.charAt(j)) {
                j += 1;
            }

            prefixFunc[i] = j;
        }

        return prefixFunc;
    }
}
