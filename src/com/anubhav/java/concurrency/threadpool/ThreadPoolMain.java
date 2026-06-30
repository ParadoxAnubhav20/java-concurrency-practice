package com.anubhav.java.concurrency.threadpool;

/**
 * Demonstrates the custom ThreadPool.
 * <p>
 * Pool Size = 3
 * <p>
 * Eleven tasks are submitted.
 * <p>
 * The first three tasks begin immediately.
 * <p>
 * Remaining tasks wait inside the BlockingQueue until a worker becomes available.
 */
public class ThreadPoolMain {

  public static void main(String[] args) throws InterruptedException {

    ThreadPool threadPool = new ThreadPool(3, 10);

    /*
     * Submit multiple tasks.
     */
    for (int i = 1; i <= 10; i++) {

      final int taskNumber = i;

      threadPool.execute(() -> {

        System.out.printf("%s executing Task-%d%n", Thread.currentThread().getName(), taskNumber);

        try {

          /*
           * Simulate work.
           */
          Thread.sleep(1000);

        } catch (InterruptedException e) {

          Thread.currentThread().interrupt();
        }

      });
    }

    /*
     * Wait until the queue becomes empty.
     */
    threadPool.waitUntilAllTasksFinished();

    /*
     * Stop all worker threads.
     */
    threadPool.stop();

    System.out.println("ThreadPool stopped.");
  }
}