package com.anubhav.java.concurrency.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Basic example demonstrating explicit locking.
 * <p>
 * Lock is an interface.
 * <p>
 * ReentrantLock is its most commonly used implementation.
 * <p>
 * Explicit locks provide greater flexibility than synchronized blocks and methods.
 */
public class LockExample {

  public static void main(String[] args) {

    /*
     * Create a new ReentrantLock.
     *
     * Initially, the lock is available
     * and is not owned by any thread.
     */
    Lock lock = new ReentrantLock();

    /*
     * Acquire the lock.
     *
     * If another thread already owns it,
     * the current thread waits until it
     * becomes available.
     */
    lock.lock();

    try {

      /*
       * -------------------------
       * Critical Section
       * -------------------------
       *
       * Only one thread can execute
       * this code at a time.
       */

      System.out.println(
          Thread.currentThread().getName()
              + " acquired the lock."
      );

      // Simulate some work.
      Thread.sleep(1000);

      System.out.println(
          Thread.currentThread().getName()
              + " finished the work."
      );

    } catch (InterruptedException e) {

      /*
       * Restore the interrupt status.
       */
      Thread.currentThread().interrupt();

    } finally {

      /*
       * Always release the lock.
       *
       * Forgetting to unlock is one of the
       * most common concurrency bugs.
       */
      lock.unlock();

      System.out.println(
          Thread.currentThread().getName()
              + " released the lock."
      );
    }
  }
}