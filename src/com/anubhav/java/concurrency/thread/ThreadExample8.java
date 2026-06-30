package com.anubhav.java.concurrency.thread;

public class ThreadExample8 {

  public static class StoppableRunnable implements Runnable {

    private boolean stopRequested = false;

    public synchronized void requestStop() {
      this.stopRequested = true;
    }

    public synchronized boolean isStopRequested() {
      return this.stopRequested;
    }

    private void sleep(long millis) {
      try {
        Thread.sleep(millis);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    @Override
    public void run() {
      System.out.println("StoppableRunnable is running");
      while (!stopRequested) {
        sleep(500);
        System.out.println("...");
      }
      System.out.println("StoppableRunnable is stopped");
    }
  }

  public static void main(String[] args) {
    StoppableRunnable stoppableRunnable = new StoppableRunnable();
    Thread thread = new Thread(stoppableRunnable, "The Thread");
    thread.start();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Requesting stop");
    stoppableRunnable.requestStop();
    System.out.println("StoppableRunnable is stopped");
  }
}
