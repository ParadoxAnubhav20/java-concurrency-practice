package com.anubhav.java.concurrency.deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Demonstrates a classic deadlock.
 *
 * Thread-A:
 *      acquires Lock-1
 *      waits
 *      attempts Lock-2
 *
 * Thread-B:
 *      acquires Lock-2
 *      waits
 *      attempts Lock-1
 *
 * Since each thread is waiting for the other lock,
 * neither can continue -> DEADLOCK.
 */
public class DeadlockExample {

  public static void main(String[] args) {

    // Shared resources
    Lock lock1 = new ReentrantLock();
    Lock lock2 = new ReentrantLock();

    Thread threadA = new Thread(
        new Runnable1(lock1, lock2),
        "Thread-A");

    Thread threadB = new Thread(
        new Runnable2(lock2, lock1),
        "Thread-B");

    threadA.start();
    threadB.start();
  }
}