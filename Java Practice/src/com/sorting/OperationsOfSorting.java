package com.sorting;

public class OperationsOfSorting {
	private int[] array;
	private int nItems;
	
	//Define Constructor
	public OperationsOfSorting(int max){
		array = new int[max];
		nItems = 0;
	}
	
	//Add data to array
	public void insert(int n){
		array[nItems] = n;
		nItems++;
	}
	
	//Display array
	public void display(){
		for(int i =0 ; i<array.length; i++ ){
			System.out.print(array[i] + " ");
		}
		System.out.println(" ");
	}
	
	//Method for bubble sort
	//Step 1: Start Loop From The end
	//Step 2: Then Start another loop from the first to the first loop
	//Step 3: Compare current element to the next array element
	//Step 4: If current element is greater then swap	
	public void bubbleSort(){
		int out, in;
		
		for(out = nItems - 1; out > 1; out --){
			for(in = 0; in < out; in++){
				if(array[in] > array [in + 1]){
					swap(in, in+1);
				}
			}
		}
	}
	
	//Method for selection sort
	public void selectioSort(){
		int in,out,min;
		
		for(out = 0; out < nItems - 1; out++){
			min = out;
			for(in = out + 1; in<nItems; in++){
				if(array[in] < array[min]){
					min = in;
					swap(out, min);
				}
			}
		}
	}
	
	
	//Method for insertion sort
	public void insertionSort(){
		int in,out;
		
		for(out = 1; out<nItems; out++){
			int temp = array[out];
			in = out ;
			
			while(in > 0 && array[in-1] >= temp){
				array[in] = array[in-1];
				--in;
			}
			array[in] = temp;
		}
	}
	
	
	
	//Method for swaping
	public void swap(int one, int two){
		
		int temp = array[one];
		array[one] = array[two];
		array[two] = temp;
	}
}
