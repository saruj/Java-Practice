package com.sorting;

public class InsertionSort {
	public static void main(String args[]){
		OperationsOfSorting oos = new OperationsOfSorting(5);
		
		oos.insert(30);
		oos.insert(40);
		oos.insert(10);
		oos.insert(50);
		oos.insert(20);
		
		oos.display();
		
		oos.insertionSort();
		
		oos.display();
		
	}
}
