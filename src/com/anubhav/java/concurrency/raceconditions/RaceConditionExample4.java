package com.anubhav.java.concurrency.raceconditions;


public class RaceConditionExample4 {

  public static void main(String[] args) {
    Counter counter1 = new Counter();
    Counter counter2 = new Counter();
    Thread thread1 = new Thread(getRunnable(counter1, counter2, "Thread 1"));
    Thread thread2 = new Thread(getRunnable(counter2, counter1, "Thread 2"));
    thread1.start();
    thread2.start();
  }

  private static Runnable getRunnable(Counter counterA, Counter counterB, String ruunableName) {
    return () -> {
      for (int i = 0; i < 1_000_000; i++) {
        counterA.incAndGet();
      }
      System.out.println(ruunableName + " final count - Counter A : " + counterA.get());
      System.out.println(ruunableName + " final count - Counter B : " + counterB.get());
    };
  }


}
