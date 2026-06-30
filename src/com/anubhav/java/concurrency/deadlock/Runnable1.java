package com.anubhav.java.concurrency.deadlock;

import java.util.concurrent.locks.Lock;

/**
 * Thread-A
 *
 * Lock acquisition order:
 *      Lock-1 -> Lock-2
 */
public class Runnable1 implements Runnable {

  // Locks are immutable after construction.
  private final Lock lock1;
  private final Lock lock2;

  public Runnable1(Lock lock1, Lock lock2) {
    this.lock1 = lock1;
    this.lock2 = lock2;
  }

  @Override
  public void run() {

    String threadName = Thread.currentThread().getName();

    System.out.println(threadName + " attempting to acquire Lock-1");

    // Acquire first lock.
    lock1.lock();

    try {

      System.out.println(threadName + " acquired Lock-1");

      // Give the other thread enough time
      // to acquire the second lock.
      Thread.sleep(3000);

      System.out.println(threadName + " attempting to acquire Lock-2");

      // Deadlock happens here.
      lock2.lock();

      try {

        System.out.println(threadName + " acquired Lock-2");

        System.out.println(threadName + " is executing critical section.");

      } finally {

        System.out.println(threadName + " releasing Lock-2");
        lock2.unlock();
      }

    } catch (InterruptedException e) {

      // Restore interrupt status.
      Thread.currentThread().interrupt();

    } finally {

      System.out.println(threadName + " releasing Lock-1");
      lock1.unlock();
    }
  }
}