package com.stack;

public class StackOfChar_2 {
	private int maxsize;
	private char stackArray[];
	private int top;
	
	public StackOfChar_2(int s){
		maxsize = s;
		stackArray = new char[maxsize];
		top = -1;
	}
	
	public void push(char c){
		stackArray[++top] = c;
	}
	
	public char pop(){
		return stackArray[top--];
	}
	
	public char peek(){
		return stackArray[top];
	}
	
	public boolean isEmpty(){
		return (top == -1);
	}
}
