package com.anubhav.java.concurrency.memory;

/**
 * Demonstrates sharing ONE Runnable object between multiple threads.
 * <p>
 * Both threads execute the same Runnable instance.
 * <p>
 * Therefore:
 * <p>
 * count shared
 * <p>
 * myObject shared
 * <p>
 * Synchronization is now necessary because both threads modify the same count variable.
 */
public class SharedObjects {

  public static void main(String[] args) {

    /*
     * Shared object.
     */
    MyObject myObject = new MyObject();

    /*
     * Only ONE Runnable object is created.
     */
    Runnable sharedRunnable =
        new MyRunnable(myObject);

    /*
     * Both threads execute the SAME Runnable.
     */
    Thread thread1 =
        new Thread(sharedRunnable, "Thread-1");

    Thread thread2 =
        new Thread(sharedRunnable, "Thread-2");

    thread1.start();
    thread2.start();
  }
}