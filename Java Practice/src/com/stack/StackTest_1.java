package com.stack;

public class StackTest_1 {
	public static void main(String args[]){
		Stack_1 s = new Stack_1(10);
		
		s.push(20);
		s.push(30);
		s.push(50);
		
		while( !s.isEmpty()){
			int value = s.pop();
			System.out.println("Value : " + value);
		}
	}
}


//Steps For Creating Stack
//Step 1: Create a class with three field : maxsize, stack array, top
//Step 2: Constructor with maxsize
//Step 3: Method push by increasing top value and storing the value
//Step 4: Method pop by decreasing the top and return
//Step 5: Method peak by returning top
//Step 5: Method boolean isEmpty by checking (top == -1)
//Step 6: Method boolean isFull by checking (top == maxsize -1)