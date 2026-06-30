package com.anubhav.java.concurrency.executorservice;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates the ExecutorService shutdown lifecycle.
 *
 * shutdown()
 *      - Stops accepting new tasks.
 *      - Already submitted tasks continue executing.
 *
 * shutdownNow()
 *      - Attempts to interrupt currently running tasks.
 *      - Returns tasks that never started execution.
 *
 * Note:
 * shutdownNow() is a "best effort".
 * It does NOT guarantee immediate termination.
 */
public class ExecutorServiceExample7 {

  public static void main(String[] args) {

    final ExecutorService executorService =
        Executors.newFixedThreadPool(2);

    /*
     * Submit more tasks than available worker threads.
     *
     * Pool Size = 2
     * Tasks Submitted = 5
     *
     * Two tasks start immediately.
     * Three wait inside the queue.
     */
    for (int i = 1; i <= 5; i++) {

      int taskNumber = i;

      executorService.execute(() -> {

        try {

          System.out.printf(
              "%s started Task-%d%n",
              Thread.currentThread().getName(),
              taskNumber
          );

          Thread.sleep(5000);

          System.out.printf(
              "%s finished Task-%d%n",
              Thread.currentThread().getName(),
              taskNumber
          );

        } catch (InterruptedException e) {

          System.out.printf(
              "%s interrupted while executing Task-%d%n",
              Thread.currentThread().getName(),
              taskNumber
          );

          Thread.currentThread().interrupt();
        }
      });
    }

    /*
     * Wait a little before forcing shutdown.
     *
     * During this time:
     * Two tasks are running.
     * Remaining tasks stay in the queue.
     */
    try {

      Thread.sleep(1000);

    } catch (InterruptedException e) {

      Thread.currentThread().interrupt();
    }

    /*
     * Attempts to stop all executing tasks.
     *
     * Returns tasks that never started execution.
     */
    List<Runnable> pendingTasks = executorService.shutdownNow();

    System.out.println();
    System.out.println("Tasks never started : " + pendingTasks.size());

    try {

      if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {

        System.out.println("Executor did not terminate.");

      }

    } catch (InterruptedException e) {

      Thread.currentThread().interrupt();
    }
  }
}