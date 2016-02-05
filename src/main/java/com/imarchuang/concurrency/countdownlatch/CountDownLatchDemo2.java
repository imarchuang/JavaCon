package com.imarchuang.concurrency.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Observer;
import java.util.Observable;

public class CountDownLatchDemo2 {

	public static void main(String[] args) throws Exception{
		// should be called twice
		   final CountDownLatch testLatch = new CountDownLatch(2);
		   ExecutorService executor = Executors.newFixedThreadPool(1);
		    
		   AsyncProcessor processor = new AsyncProcessor(new Observer() {
			      // this observer would be the analog for a listener in your async process
			      public void update(Observable o, Object arg) {
			         System.out.println("Counting down...");
			         testLatch.countDown();
			      }});
			      
		   //submit two tasks to be process
		   // (in the real world example, these could be JMS messages)
		   executor.submit(processor);
		   executor.submit(processor);

		   System.out.println("Submitted tasks. Time to wait...");
		   long time = System.currentTimeMillis();
		   testLatch.await(5000, TimeUnit.MILLISECONDS); // bail after a reasonable time
		   long totalTime = System.currentTimeMillis() - time;

		   System.out.println("I awaited for " + totalTime +
		                      "ms. Did latch count down? " + (testLatch.getCount() == 0));

		   executor.shutdown();
	}

}
