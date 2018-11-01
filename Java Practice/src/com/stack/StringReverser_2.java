package com.stack;

public class StringReverser_2 {
	private String input;
	private String output = "";
	
	public StringReverser_2(String n){
		input = n;
	}
	
	public String reverseString(){
		int stackSize = input.length();
		StackOfChar_2 s = new StackOfChar_2(stackSize);
		
		for(int i= 0 ; i < stackSize; i++){
			char ch = input.charAt(i);
			s.push(ch);
		}
		
		while(!s.isEmpty()){
			char c = s.pop();
			output += c;
		}
		
		return output;
	}
}
