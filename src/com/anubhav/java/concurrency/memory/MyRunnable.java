package com.anubhav.java.concurrency.memory;

/**
 * Demonstrates how Runnable instances behave when shared
 * between multiple threads.
 *
 * Important:
 * A Runnable is simply an object.
 *
 * If two threads receive the SAME Runnable instance,
 * they also share all of its instance variables.
 *
 * If each thread receives a DIFFERENT Runnable instance,
 * each thread has its own copy of the instance variables.
 */
public class MyRunnable implements Runnable {

  /*
   * Instance variable.
   *
   * Whether this variable is shared depends entirely
   * on whether the Runnable object itself is shared.
   */
  private int count = 0;

  /*
   * Reference to another object.
   *
   * Multiple Runnable objects may point to the
   * same MyObject instance.
   */
  private final MyObject myObject;

  public MyRunnable(MyObject myObject) {
    this.myObject = myObject;
  }

  @Override
  public void run() {

    /*
     * Print the object's identity.
     *
     * If two threads print the same hash code,
     * they are referencing the same MyObject instance.
     */
    System.out.println(
        Thread.currentThread().getName()
            + " -> MyObject : "
            + myObject
    );

    /*
     * Increment the counter one million times.
     */
    for (int i = 0; i < 1_000_000; i++) {

      /*
       * Synchronize on the current Runnable object.
       *
       * If both threads share this Runnable,
       * synchronization protects the shared count.
       *
       * If each thread owns its own Runnable,
       * synchronization has no interaction with
       * the other thread because each Runnable
       * has its own monitor.
       */
      synchronized (this) {

        count++;
      }
    }

    System.out.printf(
        "%s -> Final Count = %d%n",
        Thread.currentThread().getName(),
        count
    );
  }
}