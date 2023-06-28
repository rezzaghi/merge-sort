package mergeSort;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class Main {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

	    ForkJoinPool pool = new ForkJoinPool();
        Random rand = new Random();
		Vector<Integer> teste = new Vector<Integer>();
        Instant start = Instant.now();
        for (int i = 0; i < 100000; i++) {
            int num = rand.nextInt();
            teste.add(num);
        }

		MergeSort sort = new MergeSort(teste);

		System.out.println(pool.invoke(sort));
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");

	}
}