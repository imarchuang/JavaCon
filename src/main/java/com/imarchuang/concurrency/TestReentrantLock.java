package com.imarchuang.concurrency;

import java.util.concurrent.locks.ReentrantLock;

class TestReentrantLock {
	
	private ReentrantLock lock = new ReentrantLock();
	
	public void print(int i){
		
		try {
			lock.lock();
			System.out.println(i+" possessed");
			Thread.sleep((int)(Math.random()*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.out.println(i+" released");
			lock.unlock();
		}
	}
}
