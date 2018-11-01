package com.stack;

public class DelimeterChecking {
	
	public static void main(String ags[]){
		String s = "a{b(c]d}e";
		DelimeterChecker dc = new DelimeterChecker(s);
		
		dc.checkDelimeter();
	}
}
