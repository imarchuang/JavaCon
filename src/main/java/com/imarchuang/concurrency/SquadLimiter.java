package com.imarchuang.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SquadLimiter extends Thread {

	Semaphore position;
	private int id;
	
	public SquadLimiter(int i, Semaphore s){
		this.id = i;
		this.position = s;
	}
	
	public void run(){
		try {	
			if(position.availablePermits() > 0){
				System.out.println("Customer["+this.id+"] entered, vacancy avail");
			} else {
				System.out.println("Customer["+this.id+"] entered, no vacancy, and please wait");
			}

			position.acquire();
			System.out.println("Customer["+this.id+"] got the newly avail vacancy");
			Thread.sleep((long) (Math.random()*1000));
			System.out.println("Customer["+this.id+"] finished using the vacancy");
			position.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
	}
	
	public static void main(String[] args) {
		ExecutorService list = Executors.newCachedThreadPool();
		Semaphore position = new Semaphore(2);
		
		for(int i=0;i<10;i++){
			list.submit(new SquadLimiter(i+1,position));
	    }
	    list.shutdown();

	    position.acquireUninterruptibly(2);

	    System.out.println("Time to clean up the squads");

	    position.release(2);
	}

}
