package Lab0;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays;

public class SortData {
    public static void main(String[] args) {
        int [] numbers = new int[100];


        //TODO: Get Input from input.txt, store values in array
        File inputFile = new File("input.txt");
        if (inputFile.exists()) {
            try {
                Scanner fin = new Scanner(inputFile);

                for(int i = 0; i < 100; i++) {
                    numbers[i] = fin.nextInt();
                }
                
                fin.close();
            
            } catch (FileNotFoundException ex) {
                System.out.println("File Input Error");
            }
        } else {
            System.out.println("File not found");
        }

        //TODO: Sort the array. HINT: Look at your imports.
        Arrays.sort(numbers);

        //TODO: Write output to output.txt
        try {
            File outputFile = new File("output.txt");
            PrintWriter fout = new PrintWriter(outputFile);
            for (int i = 0; i < 100; i++) {
                fout.println(numbers[i]);
            }
        fout.close();
        } catch (FileNotFoundException e) {
            System.out.println("Output Error");
        }
    }
}
