package com.anubhav.java.concurrency.threadpool;

import java.util.concurrent.BlockingQueue;

/**
 * Represents one worker thread inside the pool.
 * <p>
 * The worker continuously:
 * <p>
 * 1. waits for a task 2. removes it from the queue 3. executes it 4. repeats forever
 * <p>
 * until the pool is stopped.
 */
public class PoolThreadRunnable implements Runnable {

  /*
   * Thread executing this worker.
   */
  private Thread thread;

  /*
   * Shared task queue.
   */
  private final BlockingQueue<Runnable> taskQueue;

  /*
   * Indicates whether this worker has stopped.
   */
  private boolean isStopped = false;

  public PoolThreadRunnable(
      BlockingQueue<Runnable> taskQueue) {

    this.taskQueue = taskQueue;
  }

  @Override
  public void run() {

    thread = Thread.currentThread();

    while (!isStopped()) {

      try {

        /*
         * take() blocks until
         * a task becomes available.
         */
        Runnable task = taskQueue.take();

        /*
         * Execute the submitted task.
         */
        task.run();

      } catch (InterruptedException e) {

        /*
         * Expected when stopping
         * the thread pool.
         */
        Thread.currentThread().interrupt();

      } catch (Exception e) {

        /*
         * One bad task should NOT
         * terminate the worker thread.
         */
        e.printStackTrace();
      }
    }
  }

  /**
   * Stops this worker thread.
   */
  public synchronized void doStop() {

    isStopped = true;

    /*
     * Interrupt take()
     * if currently blocked.
     */
    thread.interrupt();
  }

  /**
   * Returns whether this worker has stopped.
   */
  public synchronized boolean isStopped() {

    return isStopped;
  }
}