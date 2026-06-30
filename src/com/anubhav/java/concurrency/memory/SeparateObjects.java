package com.anubhav.java.concurrency.memory;

/**
 * Demonstrates two DIFFERENT Runnable objects.
 * <p>
 * Each thread receives its own Runnable instance.
 * <p>
 * Therefore: - count is NOT shared. - each thread increments its own counter.
 * <p>
 * However...
 * <p>
 * Both Runnable objects reference the SAME MyObject.
 * <p>
 * So: count      -> separate myObject   -> shared
 */
public class SeparateObjects {

  public static void main(String[] args) {

    /*
     * One shared object.
     */
    MyObject myObject = new MyObject();

    /*
     * Two completely different Runnable objects.
     */
    Runnable runnable1 = new MyRunnable(myObject);
    Runnable runnable2 = new MyRunnable(myObject);

    Thread thread1 =
        new Thread(runnable1, "Thread-1");

    Thread thread2 =
        new Thread(runnable2, "Thread-2");

    thread1.start();
    thread2.start();
  }
}