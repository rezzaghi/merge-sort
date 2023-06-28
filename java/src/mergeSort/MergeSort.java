package mergeSort;

import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class MergeSort extends RecursiveTask<Vector<Integer>> {
	private final Vector<Integer> list;
	
	public MergeSort(Vector<Integer> list) {
		this.list = list;
	}
	
	
	@Override
	protected Vector<Integer> compute(){
		if (list.size() <= 1) {
			return list;
		}
		Vector<Integer> left = new Vector<Integer>();
		Vector<Integer> right = new Vector<Integer>();

		for (int i = 0; i < list.size(); i++) {
			if (i < list.size() / 2) {
				left.add(list.get(i));
			} else {
				right.add(list.get(i));
			}
		}

        MergeSort _left = new MergeSort(left);
        MergeSort _right = new MergeSort(right);

        _left.fork();
        _right.fork();

        Vector<Integer> leftSorted = _left.join();
        Vector<Integer> rightSorted = _right.join();
		
		
		return merge(leftSorted, rightSorted);
		
	}

	static Vector<Integer> merge(Vector<Integer> left, Vector<Integer> right) {
		Vector<Integer> result = new Vector<Integer>();

		while (!left.isEmpty() && !right.isEmpty()) {
			if (left.get(0) <= right.get(0)) {
				result.add(left.get(0));
				left.remove(0);
			} else {
				result.add(right.get(0));
				right.remove(0);
			}
		}
		while (!left.isEmpty()) {
			result.add(left.get(0));
			left.remove(0);

		}
		while (!right.isEmpty()) {
			result.add(right.get(0));
			right.remove(0);
		}

		return result;
	}
}
