package com.imarchuang.concurrency.countdownlatch;

import java.util.Observer;
import java.util.Random;
import java.util.concurrent.Callable;

public class AsyncProcessor implements Callable<Object> {

	private Observer listener;

	AsyncProcessor(Observer observer) {
		this.listener = observer;
	}

	public Object call() throws Exception {
		// some processing here which can take all kinds of time...

		int sleepTime = new Random().nextInt(2000);

		System.out.println("Sleeping for " + sleepTime + "ms");

		Thread.sleep(sleepTime);
		System.out.println("Calling update method of the observer");
		listener.update(null, null); // not standard usage, but good for a demo

		return null;
	}

}
