package com.anubhav.java.concurrency.locks;

/**
 * Thread-safe counter implemented using synchronized methods.
 *
 * The synchronized keyword provides mutual exclusion.
 *
 * When one thread enters a synchronized method,
 * every other thread attempting to enter another
 * synchronized method of the same object must wait.
 *
 * The JVM automatically:
 * - acquires the object's monitor before entering
 * - releases the monitor when leaving the method
 *
 * Unlike Lock, developers never explicitly acquire
 * or release the monitor.
 */
public class CounterSynchronized {

  /*
   * Shared mutable state.
   */
  private long count = 0;

  /**
   * Increments the counter.
   *
   * The intrinsic monitor of this object
   * (this) is automatically acquired.
   */
  public synchronized void increment() {

    count++;
  }

  /**
   * Returns the current value.
   *
   * Synchronization ensures that the latest value
   * written by one thread becomes visible
   * to other threads.
   */
  public synchronized long getCount() {

    return count;
  }
}