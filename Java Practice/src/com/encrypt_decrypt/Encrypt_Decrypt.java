package com.encrypt_decrypt;

import java.util.Base64;

public class Encrypt_Decrypt {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "Saruj12345000";
		
		// encode data on your side using BASE64
		byte[]   bytesEncoded =  Base64.getEncoder().encode(str.getBytes());
		System.out.println("ecncoded value is " + new String(bytesEncoded ));

		// Decode data on other side, by processing encoded data
		byte[] valueDecoded= Base64.getDecoder().decode(bytesEncoded);
		
		//byte[] valueDecoded= Base64.getDecoder().decode(str.getBytes());
		System.out.println("Decoded value is " + new String(valueDecoded));

	}

}
