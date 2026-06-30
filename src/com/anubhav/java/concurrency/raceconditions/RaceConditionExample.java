package com.anubhav.java.concurrency.raceconditions;



public class RaceConditionExample {

  public static void main(String[] args) {
    Counter counter = new Counter();
    Thread thread1 = new Thread(getRunnable(counter, "Thread1 Final Count : "));
    Thread thread2 = new Thread(getRunnable(counter, "Thread2 Final Count : "));
    thread1.start();
    thread2.start();
  }

  private static Runnable getRunnable(Counter counter, String msg) {
    return () -> {
      for (int i = 0; i < 1_000_000; i++) {
        counter.incAndGet();
      }
      System.out.println(msg + counter.get());
    };
  }
}
