package com.queue;

public class Queue {
	private int maxsize;
	private int[] queueArray;
	private int front;
	private int rear;
	private int nItems;
	
	public Queue(int s){
		maxsize = s;
		queueArray = new int[maxsize];
		front = 0;
		rear = -1;
		nItems = 0;
	}
	
	public void insert(int n){
		if(rear == maxsize -1){
			rear = -1;
		}
		queueArray[++rear] = n;
		nItems++;
	}
	
	public int remove(){
		int temp = queueArray[front++];
		if(front == maxsize){
			front = 0;
		}
		nItems--;
		return temp;			
	}
	
	public int peekFront(){
		return queueArray[front];
	}
	
	public boolean isEmpty(){
		return (nItems == 0);
	}
	
	public boolean isFull(){
		return (nItems == maxsize);
	}
	
	public int size(){
		return nItems;
	}
}

