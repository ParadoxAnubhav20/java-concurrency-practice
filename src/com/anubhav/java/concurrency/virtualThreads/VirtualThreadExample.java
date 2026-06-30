package com.anubhav.java.concurrency.virtualThreads;

public class VirtualThreadExample {

  public static void main(String[] args) {
    //1) Create Runnable, Create and Start Virtual Thread
    Runnable r = () -> {
      for (int i = 0; i <= 10; i++) {
        System.out.println("Index : " + i);
      }
    };
    Thread vThread1 = Thread.ofVirtual().start(r);
    //2) Creating but not starting the virtual thread
    Thread vThreadUnstarted = Thread.ofVirtual().unstarted(r);
    vThreadUnstarted.start();
    try {
      vThreadUnstarted.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
