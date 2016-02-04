package com.imarchuang.concurrency;

import java.util.concurrent.Semaphore;

public class DeadlockSemaphore {

	public static void main(String[] args) throws Exception{
		Semaphore s1 = new Semaphore(1);
		Semaphore s2 = new Semaphore(1);
		
		Thread t1 = new Thread(new DoubleResourceGrabber(s1,s2));
		// create a deadlock thread to compete the resource
		Thread t2 = new Thread(new DoubleResourceGrabber(s2,s1));
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("We got lucky!");
	}
	
	private static class DoubleResourceGrabber implements Runnable {

		Semaphore begin;
		Semaphore end;
		
		public DoubleResourceGrabber(Semaphore s1, Semaphore s2) {
			begin = s1;
			end = s2;
		}

		public void run() {
			try{
				Thread t = Thread.currentThread();
				begin.acquire();
				System.out.println(t + " acquired " + begin);
				
				Thread.sleep(2000); // demonstrate deadlock
				
				end.acquire();
				System.out.println(t + " acquired " + end);
				
				end.release();
				System.out.println(t + " released " + end);
				
				begin.release();
				System.out.println(t + " release " + begin);
				
			} catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
		
	}

}
