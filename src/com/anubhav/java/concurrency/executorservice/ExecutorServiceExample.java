package com.anubhav.java.concurrency.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates the basic usage of an ExecutorService.
 *
 * Instead of manually creating Thread objects, we submit tasks to a thread pool.
 * The ExecutorService manages thread creation, scheduling, reuse, and destruction.
 *
 * In this example:
 * - A fixed thread pool of size 3 is created.
 * - Five Runnable tasks are submitted.
 * - The same worker threads are reused to execute multiple tasks.
 */
public class ExecutorServiceExample {

  public static void main(String[] args) {

    /*
     * Creates a thread pool containing exactly 3 worker threads.
     *
     * If more than 3 tasks are submitted:
     *      - The first 3 begin executing immediately.
     *      - Remaining tasks wait in an internal queue.
     */
    final ExecutorService executorService = Executors.newFixedThreadPool(3);

    // Submit five independent tasks.
    executorService.execute(createTask("Task-1"));
    executorService.execute(createTask("Task-2"));
    executorService.execute(createTask("Task-3"));
    executorService.execute(createTask("Task-4"));
    executorService.execute(createTask("Task-5"));

    /*
     * Prevents any new tasks from being submitted.
     *
     * Already submitted tasks are allowed to finish normally.
     */
    executorService.shutdown();

    /*
     * Wait for all submitted tasks to complete.
     *
     * This is the recommended shutdown pattern.
     */
    try {

      if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {

        System.out.println("Forcing ExecutorService shutdown...");

        // Interrupt running tasks.
        executorService.shutdownNow();
      }

    } catch (InterruptedException e) {

      executorService.shutdownNow();

      // Restore interrupt status.
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Creates a Runnable task.
   *
   * Runnable:
   * - represents a unit of work.
   * - does NOT return a result.
   * - cannot throw checked exceptions.
   */
  private static Runnable createTask(String taskName) {

    return () -> {

      System.out.printf(
          "%s is executing %s%n",
          Thread.currentThread().getName(),
          taskName
      );

    };
  }
}