package com.thread;

public class Deadlock {
	static class Friend{
		private final String name;
		
		public Friend(String name){
			this.name = name ;
		}
		
		public String getName(){
			return this.name;
		}
		
		public synchronized void bow (Friend bower){
			System.out.println(bower.getName() + " has bowed to me");
			bower.bowback(this);
		}
		
		public synchronized void bowback (Friend bower){
			System.out.println(bower.getName() + " has bowed back to me!");
		}
	}
	
	public static void main(String args[]){
		final Friend alphonse =
	            new Friend("Alphonse");
	        final Friend gaston =
	            new Friend("Gaston");
	        new Thread(new Runnable() {
	            public void run() { alphonse.bow(gaston); }
	        }).start();
	        new Thread(new Runnable() {
	            public void run() { gaston.bow(alphonse); }
	        }).start();
	}
}