/**
 * Java’s synchronized keyword is a wonderful tool – it allows us a simple 
 * and reliable way to synchronize access to critical sections and it’s not too hard to understand.
 * 
 * But sometimes we need more control over synchronization. Either we need to control types of access 
 * (read and write) separately, or it is cumbersome to use because either there is no obvious 
 * mutex or we need to maintain multiple mutexes.
 * 
 */

package com.imarchuang.concurrency;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

	//Traditional way of using synchronized keyword on the method
	/*
	private int calculatedValue;

    private int value;

    public synchronized void calculate(int value) {

        this.value = value;

        this.calculatedValue = doMySlowCalculation(value);

    }

    public synchronized int getCalculatedValue() {

        return calculatedValue;

    }

    public synchronized int getValue() {

        return value;
    }
	*/
	
	//using read write lock
	
	private int calculatedValue;
    private int value;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    
    public synchronized void calculate(int value) {

    	lock.writeLock().lock();
    	try{
	        this.value = value;
	        this.calculatedValue = doMySlowCalculation(value);
    	} finally {
    		lock.writeLock().unlock();
    	}

    }

    private int doMySlowCalculation(int value) {
		// TODO Auto-generated method stub
    	try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public synchronized int getCalculatedValue() {
		lock.readLock().lock();
		try{
			return calculatedValue;
		} finally {
			lock.readLock().unlock();
		}

    }

    public synchronized int getValue() {
    	lock.readLock().lock();
		try{
			return value;
		} finally {
			lock.readLock().unlock();
		}
    }
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	

}
