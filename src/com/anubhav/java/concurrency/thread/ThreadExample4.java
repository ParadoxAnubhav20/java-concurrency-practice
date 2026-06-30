package com.anubhav.java.concurrency.thread;

public class ThreadExample4 {

  public static void main(String[] args) {
    Runnable r = new Runnable() {
      @Override
      public void run() {
        System.out.println("Runnable running");
        System.out.println("Runnable Finished");
      }
    };
    Thread t = new Thread(r);
    t.start();
  }
}
