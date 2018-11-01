package com.stack;

public class DelimeterChecker {
	private String input;
	
	public DelimeterChecker(String s){
		input = s;
	}
	
	public void checkDelimeter(){
		int length = input.length();
		StackOfChar_2 soc = new StackOfChar_2(length);
		
		for(int i =0; i<length; i++){
			char ch = input.charAt(i);
			switch(ch){
				case '[':
				case '{':
				case '(':
					soc.push(ch);
					break;
				case ']':
				case '}':
				case ')':
					if(!soc.isEmpty()){
						char c = soc.pop();
						if((ch == ')' && c != '(') ||
						   (ch == '}' && c != '{') ||
						   (ch == ']' && c != '[')){
							System.out.println("Error : "+ ch + " at " + (i+1) );
						}
					}else{
						System.out.println("Error : "+ ch + " at " + (i+1) );
						break;
					}
				default:
					break;
			}
			
		}
		if(!soc.isEmpty()){
			System.out.println("Missing right parentheses....");
		}
		
	}
}
