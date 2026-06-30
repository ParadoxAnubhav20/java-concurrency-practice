package com.anubhav.java.concurrency.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates a Single-Thread Executor.
 *
 * Only ONE worker thread exists.
 *
 * All submitted tasks execute sequentially in submission order.
 *
 * Task-1
 * ↓
 * Task-2
 * ↓
 * Task-3
 *
 * No two tasks execute simultaneously.
 */
public class ExecutorServiceExample2 {

  public static void main(String[] args) {

    /*
     * Creates an ExecutorService with exactly one worker thread.
     *
     * Every submitted task is placed into a queue.
     * The single worker thread processes one task at a time.
     */
    final ExecutorService executorService = Executors.newFixedThreadPool(1);

    executorService.execute(createTask("Task-1.1"));
    executorService.execute(createTask("Task-1.2"));
    executorService.execute(createTask("Task-1.3"));

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
   * Creates a simple Runnable task.
   */
  private static Runnable createTask(String taskName) {

    return () -> {

      System.out.printf(
          "%s executed %s%n",
          Thread.currentThread().getName(),
          taskName
      );

    };
  }
}