package mergeSort;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Vector;
import java.util.concurrent.ForkJoinPool;

public class Main {

	public static void main(String[] args) {

		   String filePath = "./10000.txt"; 
	        Vector<Integer> list = new Vector<>();

	        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                try {
	                    int number = Integer.parseInt(line.trim());
	                    list.add(number); 
	                } catch (NumberFormatException e) {
	                    System.out.println("Failed to parse integer from line: " + line);
	                }
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }


	    ForkJoinPool pool = new ForkJoinPool();
		MergeSort sort = new MergeSort(list);
        Instant start = Instant.now();
		Vector<Integer> result = pool.invoke(sort);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");

	}
}