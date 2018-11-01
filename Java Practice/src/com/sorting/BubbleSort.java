package com.sorting;

public class BubbleSort {
	public static void main(String args[]){
		OperationsOfSorting o = new OperationsOfSorting(5);
		o.insert(20);
		o.insert(30);
		o.insert(10);
		o.insert(60);
		o.insert(40);
		
		o.display();
		
		o.bubbleSort();
		
		o.display();
	}
	
}
