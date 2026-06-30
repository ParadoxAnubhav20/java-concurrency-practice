package com.anubhav.java.concurrency.thread;

public class ThreadExample3 {

  public static void main(String[] args) {
    Thread thread = new Thread(new MyRunnable());
    thread.start();
  }


  public static class MyRunnable implements Runnable {

    @Override
    public void run() {
      System.out.println("MyRunnable running");
      System.out.println("MyRunnable Finished");
    }
  }

}
