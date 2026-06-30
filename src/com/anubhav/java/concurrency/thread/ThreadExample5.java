package com.anubhav.java.concurrency.thread;

public class ThreadExample5 {

  public static void main(String[] args) {
    Runnable r = () -> {
      System.out.println("Lambda running");
      System.out.println("Lambda Finished");
    };
    Thread t = new Thread(r);
    t.start();
  }
}
