package com.queue;

public class QueueTest {
	public static void main(String args[]){
		
		Queue q = new Queue(5);
		
		q.insert(10);
		q.insert(20);
		q.insert(30);
		q.insert(40);
		q.insert(50);
		
		q.remove();
		q.remove();
		
		q.insert(60);
		q.insert(70);
		
		
		
		while(!q.isEmpty()){
			int item = q.remove();
			System.out.println(item);
		}
		
		System.out.println("Front Queue:"+q.peekFront());
		
	}
}
