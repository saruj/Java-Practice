package com.queue;

public class PriorityQueue {
	private int maxsize;
	private int[] priorityArray;
	private int nItems;
	
	public PriorityQueue(int s){
		maxsize = s;
		priorityArray = new int[maxsize];
		nItems = 0;
	}
	
	public void insert(int item){
		int i = 0;
		if(nItems == 0){
			priorityArray[nItems++] = item;
		}else{
			 for(i=nItems-1; i>=0; i-- ){
				 if(item > priorityArray[i]){
					 priorityArray[i+1] = priorityArray[i];
				 }else{
					 break;
				 }
			 }
			 priorityArray[i+1] = item;
			 nItems++;
		}	
	}
	
	public int remove(){
		return (priorityArray[--nItems]);
	}
	
	public int peek(){
		return priorityArray[nItems-1];
	}
	
	public boolean isEmpty(){
		return (nItems == 0);
	}
}
