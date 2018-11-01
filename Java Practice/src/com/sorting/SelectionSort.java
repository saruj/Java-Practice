package com.sorting;

public class SelectionSort {
	public static void main(String args[]){
		OperationsOfSorting os = new OperationsOfSorting(5);
		
		os.insert(20);
		os.insert(30);
		os.insert(10);
		os.insert(60);
		os.insert(40);
		
		os.display();
		
		os.selectioSort();
		
		os.display();
	}
}
