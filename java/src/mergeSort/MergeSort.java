package mergeSort;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class MergeSort extends RecursiveTask<ArrayList<Integer>> {
	private final ArrayList<Integer> list;

	public MergeSort(ArrayList<Integer> list) {
		this.list = list;
	}
	
	
	@Override
	protected ArrayList<Integer> compute(){
		if (list.size() <= 1) {
			return list;
		}
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();

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

        ArrayList<Integer> leftSorted = _left.join();
        ArrayList<Integer> rightSorted = _right.join();
		
		
		return merge(leftSorted, rightSorted);
		
	}

	static ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right) {
		ArrayList<Integer> result = new ArrayList<Integer>();

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
