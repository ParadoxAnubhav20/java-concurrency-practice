package com.anubhav.java.concurrency.executorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates submitting a Callable task.
 *
 * Unlike Runnable:
 * - Callable can return a value.
 * - Callable can throw checked exceptions.
 *
 * submit(Callable<T>) returns a Future<T>, allowing us
 * to retrieve the computed result.
 */
public class ExecutorServiceExample4 {

  public static void main(String[] args) {

    final ExecutorService executorService = Executors.newFixedThreadPool(1);

    /*
     * Submit a Callable task.
     *
     * Since the Callable returns a String,
     * submit() returns Future<String>.
     */
    Future<String> future = executorService.submit(createTask("Task-1.1"));

    System.out.println("Task completed? " + future.isDone());

    try {

      /*
       * Blocks until the Callable finishes execution.
       *
       * Unlike Runnable, Callable returns a value.
       */
      String result = future.get();

      System.out.println("Returned Result : " + result);

    } catch (InterruptedException e) {

      Thread.currentThread().interrupt();

    } catch (ExecutionException e) {

      /*
       * ExecutionException wraps any exception thrown
       * inside the Callable.
       */
      e.printStackTrace();
    }

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
   * Creates a Callable task.
   *
   * Callable is similar to Runnable,
   * but it returns a value.
   */
  private static Callable<String> createTask(String taskName) {

    return () -> {

      // Simulate some work.
      Thread.sleep(1000);

      return Thread.currentThread().getName()
          + " executed "
          + taskName;
    };
  }
}