package com.imarchuang.concurrency.cyclicbarrier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class CyclicBarrierDemo {
  // Time taken by walk to: Shenzhen, Guangzhou, Shaoguan, Changsha, Wuhan
  private static int[] timeWalk = { 5, 8, 15, 15, 10 };
  // Time taken by car
  private static int[] timeSelf = { 1, 3, 4, 4, 5 };
  // Time taken by bus
  private static int[] timeBus = { 2, 4, 6, 6, 7 };
  
  static String now() {
     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
     return sdf.format(new Date()) + ": ";
  }
  static class Tour implements Runnable {
     private int[] times;
     private CyclicBarrier barrier;
     private String tourName;
     public Tour(CyclicBarrier barrier, String tourName, int[] times) {
       this.times = times;
       this.tourName = tourName;
       this.barrier = barrier;
     }
     public void run() {
       try {
         Thread.sleep(times[0] * 1000);
         System.out.println(now() + tourName + " Reached Shenzhen");
         barrier.await();
         Thread.sleep(times[1] * 1000);
         System.out.println(now() + tourName + " Reached Guangzhou");
         barrier.await();
         Thread.sleep(times[2] * 1000);
         System.out.println(now() + tourName + " Reached Shaoguan");
         barrier.await();
         Thread.sleep(times[3] * 1000);
         System.out.println(now() + tourName + " Reached Changsha");
         barrier.await();
         Thread.sleep(times[4] * 1000);
         System.out.println(now() + tourName + " Reached Wuhan");
         barrier.await();
       } catch (InterruptedException e) {
       } catch (BrokenBarrierException e) {
       }
     }
  }
  public static void main(String[] args) {
     // Three tour groups
     CyclicBarrier barrier = new CyclicBarrier(3);
     ExecutorService exec = Executors.newFixedThreadPool(3);
     exec.submit(new Tour(barrier, "WalkTour", timeWalk));
     exec.submit(new Tour(barrier, "SelfTour", timeSelf));
     // by commenting next line, the program will hang
     exec.submit(new Tour(barrier, "BusTour", timeBus));
     exec.shutdown();
  }
} 
