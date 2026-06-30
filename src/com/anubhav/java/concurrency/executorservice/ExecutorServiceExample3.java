package com.anubhav.java.concurrency.executorservice;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates submit(Runnable) and Future.
 *
 * Unlike execute(), submit() returns a Future object.
 *
 * Future allows us to:
 *
 * - check whether the task has completed.
 * - wait until the task finishes.
 * - cancel the task.
 * - retrieve the result (if one exists).
 *
 * Since Runnable does not return a value,
 * Future.get() returns null after successful completion.
 */
public class ExecutorServiceExample3 {

  public static void main(String[] args) {

    final ExecutorService executorService = Executors.newFixedThreadPool(1);

    /*
     * submit(Runnable)
     *
     * Unlike execute(), this returns a Future.
     */
    Future<?> future = executorService.submit(createTask("Task-1.1"));

    /*
     * Immediately after submission,
     * the task may or may not have completed.
     */
    System.out.println("Task completed? " + future.isDone());

    try {

      /*
       * Blocks the calling thread (main thread)
       * until the task completes.
       *
       * Since this Runnable returns nothing,
       * get() returns null.
       */
      Object result = future.get();

      System.out.println("Future returned: " + result);

    } catch (InterruptedException e) {

      Thread.currentThread().interrupt();

    } catch (ExecutionException e) {

      /*
       * Thrown if the task itself throws an exception.
       */
      e.printStackTrace();
    }

    /*
     * Now the task has definitely completed.
     */
    System.out.println("Task completed? " + future.isDone());

    executorService.shutdown();

    try {

      if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {

        executorService.shutdownNow();
      }

    } catch (InterruptedException e) {

      executorService.shutdownNow();
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Creates a Runnable task.
   */
  private static Runnable createTask(String taskName) {

    return () -> {

      System.out.printf(
          "%s is executing %s%n",
          Thread.currentThread().getName(),
          taskName
      );

      /*
       * Simulate some work.
       */
      try {

        Thread.sleep(1000);

      } catch (InterruptedException e) {

        Thread.currentThread().interrupt();
      }
    };
  }
}