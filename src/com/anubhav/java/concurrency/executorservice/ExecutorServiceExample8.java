package com.anubhav.java.concurrency.executorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates:
 * <p>
 * - Callable - Future - Blocking behavior of Future.get()
 * <p>
 * This example simulates a long-running computation that eventually returns an Integer.
 */
public class ExecutorServiceExample8 {

  public static void main(String[] args) {

    final ExecutorService executorService =
        Executors.newFixedThreadPool(4);

    /*
     * Submit a computation task.
     */
    Future<Integer> future =
        executorService.submit(new RandomNumberTask());

    System.out.println("Main thread continues...");

    try {

      /*
       * get() is a blocking operation.
       *
       * The main thread waits here until
       * call() completes.
       */
      Integer number = future.get();

      System.out.println("Generated Number : " + number);

    } catch (InterruptedException e) {

      Thread.currentThread().interrupt();

    } catch (ExecutionException e) {

      e.printStackTrace();
    }

    System.out.println(
        "Executed by : " + Thread.currentThread().getName()
    );

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
   * Simulates a long-running computation.
   */
  static class RandomNumberTask implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {

      System.out.println(
          Thread.currentThread().getName()
              + " started computation."
      );

      /*
       * Simulate expensive work.
       */
      Thread.sleep(1000);

      System.out.println(
          Thread.currentThread().getName()
              + " completed computation."
      );

      /*
       * ThreadLocalRandom is preferred over Random
       * in concurrent environments.
       */
      return ThreadLocalRandom.current().nextInt(1000);
    }
  }
}