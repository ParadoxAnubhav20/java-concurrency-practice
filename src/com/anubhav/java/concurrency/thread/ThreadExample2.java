package com.anubhav.java.concurrency.thread;

public class ThreadExample2 {

  public static void main(String[] args) {
    MyThread myThread = new MyThread();
    myThread.start();
  }


  public static class MyThread extends Thread {

    @Override
    public void run() {
      System.out.println("MyThread running");
      System.out.println("MyThread Finished");
    }
  }

}
