/**
 * CountDownLatch – a more general wait/notify mechanism. 
 * As you can see, it is simpler than wait/notify, and requires less code. 
 * It also allows us to invoke the condition that ultimately releases the block before we call wait(). 
 * This can mean safer code.
 */

package com.imarchuang.concurrency.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class CountdownLatchDemo {

	public static void main(String[] args) throws Exception {
		final CountDownLatch latch = new CountDownLatch(1); // just one time
		Thread t = new Thread() {
			public void run() {
				// no lock to acquire!
				System.out.println("Going to count down...");

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				latch.countDown();

			}
		};

		t.start(); // start her up and let her wait()
		System.out.println("Going to await...");
		latch.await();
		System.out.println("Done waiting!");
	}
}
