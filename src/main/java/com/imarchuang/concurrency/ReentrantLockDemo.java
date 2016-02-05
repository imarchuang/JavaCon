package com.imarchuang.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReentrantLockDemo extends Thread {

	TestReentrantLock lock;
	private int id;
	
	public ReentrantLockDemo(int i,TestReentrantLock test){
		this.id = i;
		this.lock = test;
	}
	
	public void run(){
		lock.print(id);
	}
	
	public static void main(String[] args) {
		ExecutorService service=Executors.newCachedThreadPool();
		TestReentrantLock lock = new TestReentrantLock();
		for(int i=0;i<10;i++){
			service.submit(new ReentrantLockDemo(i,lock));	
		}
			
		service.shutdown();
	}

}
