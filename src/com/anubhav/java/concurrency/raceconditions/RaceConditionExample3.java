package com.anubhav.java.concurrency.raceconditions;


public class RaceConditionExample3 {

  public static void main(String[] args) {
    Counter counter = new Counter();
    Thread thread1 = new Thread(getIncrementingRunnable(counter));
    Thread thread2 = new Thread(getReadingRunnable(counter));
    thread1.start();
    thread2.start();
  }


  private static Runnable getIncrementingRunnable(Counter counter) {
    return new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 1_000_000; i++) {
          counter.incAndGet();
        }
        System.out.println("Thread1 Final Count : " + counter.get());
      }
    };
  }

  private static Runnable getReadingRunnable(Counter counter) {
    return new Runnable() {
      @Override
      public void run() {
        for (int i = 0; i < 5; i++) {
          try {
            Thread.sleep(1);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
          System.out.println("Thread2 Reading Count : " + counter.get());
        }
      }
    };
  }
}
