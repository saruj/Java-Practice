package com.stack;

public class Stack_1 {
	private int maxsize;
	private int[] stackArray;
	private int top;
	
	public Stack_1(int x){
		maxsize = x;
		stackArray = new int[maxsize];
		top = -1;
	}
	
	public void push(int n){
		stackArray[++top] = n;
	}
	
	public int pop(){
		return stackArray[top--];
	}
	
	public int peek(){
		return stackArray[top];
	}
	
	public boolean isEmpty(){
		return (top == -1);
	}
	
	public boolean isFull(){
		return (top == maxsize - 1);
	}
}
