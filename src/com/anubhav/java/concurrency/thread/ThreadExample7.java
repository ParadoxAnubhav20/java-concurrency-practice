package com.anubhav.java.concurrency.thread;

public class ThreadExample7 {

  public static void main(String[] args) {
    Runnable r = () -> {
      String threadName = Thread.currentThread().getName();
      System.out.println(threadName + " running");
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println(threadName + " finished");
    };
    Thread t1 = new Thread(r, "The Thread-1");
    t1.start();
    Thread t2 = new Thread(r, "The Thread-2");
    t2.start();
  }
}
