package com.queue;

public class PriorityQueueTest {
	public static void main(String args[]){
		PriorityQueue pq = new PriorityQueue(5);
		
		pq.insert(10);
		pq.insert(20);
		pq.insert(40);
		pq.insert(50);
		pq.insert(30);
				
		while(!pq.isEmpty()){
			int data = pq.remove();
			System.out.println("Items : "+data);
		}
		
	}
}
