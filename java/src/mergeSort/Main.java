package mergeSort;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Main {

	public static void main(String[] args) throws NumberFormatException, IOException {
	    ForkJoinPool pool = new ForkJoinPool();
	    String filePath = "1000000.txt"; 
		ArrayList<Integer> list = new ArrayList<Integer>();
		FileInputStream fis = new FileInputStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

		String line;
		while ((line = reader.readLine()) != null) {
			list.add(Integer.parseInt(line)); 
		}

		MergeSort sort = new MergeSort(list);

        Instant start = Instant.now();
		ArrayList<Integer> result = pool.invoke(sort);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");
	}
}