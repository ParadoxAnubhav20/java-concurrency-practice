package com.anubhav.java.concurrency.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread-safe counter implemented using an explicit Lock.
 * <p>
 * Why use Lock instead of synchronized?
 * <p>
 * Lock provides additional capabilities: - tryLock() - lockInterruptibly() - fairness policies - multiple Condition
 * variables
 * <p>
 * ReentrantLock is the most commonly used implementation of the Lock interface.
 */
public class CounterLock {

  /*
   * Shared mutable state.
   *
   * Since multiple threads may modify this variable,
   * access must be synchronized.
   */
  private long count = 0;

  /*
   * A ReentrantLock protects access to the shared counter.
   *
   * Reentrant means the same thread may acquire the lock
   * multiple times without deadlocking itself.
   */
  private final Lock lock = new ReentrantLock();

  /**
   * Increments the counter.
   * <p>
   * Only one thread can execute the critical section at a time.
   */
  public void increment() {

    /*
     * Acquire the lock.
     *
     * If another thread already owns the lock,
     * the current thread blocks until the lock
     * becomes available.
     */
    lock.lock();

    try {

      count++;

    } finally {

      /*
       * Always release the lock.
       *
       * finally guarantees execution even if an
       * exception occurs inside the critical section.
       */
      lock.unlock();
    }
  }

  /**
   * Returns the current counter value.
   * <p>
   * Locking guarantees visibility and consistency.
   */
  public long getCount() {

    lock.lock();

    try {

      return count;

    } finally {

      lock.unlock();
    }
  }
}