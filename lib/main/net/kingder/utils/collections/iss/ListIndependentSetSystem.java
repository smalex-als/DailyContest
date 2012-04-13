package net.egork.collections.iss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Egor Kulikov (kulikov@devexperts.com)
 */
public class ListIndependentSetSystem implements IndependentSetSystem {
	private final ArrayList<Integer>[] lists;
	private int[] parent;
	private int setCount;
	private Listener listener;

	public ListIndependentSetSystem(int size) {
		//noinspection unchecked
		lists = new ArrayList[size];
		parent = new int[size];
		for (int i = 0; i < size; i++)
			parent[i] = i;
		setCount = size;
	}

	public boolean join(int first, int second) {
		first = parent[first];
		second = parent[second];
		if (first == second)
			return false;
		int firstSize = lists[first] == null ? 1 : lists[first].size() + 1;
		int secondSize = lists[second] == null ? 1 : lists[second].size() + 1;
		if (firstSize < secondSize) {
			int temp = first;
			first = second;
			second = temp;
		}
		if (lists[first] == null)
			lists[first] = new ArrayList<Integer>();
		lists[first].ensureCapacity(firstSize + secondSize - 1);
		lists[first].add(second);
		parent[second] = first;
		if (lists[second] != null) {
			for (int child : lists[second]) {
				lists[first].add(child);
				parent[child] = first;
			}
		}
		lists[second] = null;
		if (listener != null)
			listener.joined(second, first);
		setCount--;
		return true;
	}

	public int get(int index) {
		return parent[index];
	}

	public int getSetCount() {
		return setCount;
	}

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public List<Integer> getChildren(int vertex) {
		if (parent[vertex] != vertex)
			throw new IllegalArgumentException();
		return lists[vertex] == null ? Collections.<Integer>emptyList() : lists[vertex];
	}
}
