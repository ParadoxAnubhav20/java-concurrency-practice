package com.anubhav.java.concurrency.executorservice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Demonstrates Java Virtual Threads (Project Loom).
 * <p>
 * Virtual Threads were introduced to make concurrent programming much simpler and more scalable than traditional
 * platform threads.
 * <p>
 * ------------------------------------------------------------------------- Platform Thread (Traditional Thread)
 * ------------------------------------------------------------------------- - Backed directly by an Operating System
 * thread. - Expensive to create. - Consumes a relatively large amount of memory. - Thousands of threads can overwhelm
 * the operating system.
 * <p>
 * ------------------------------------------------------------------------- Virtual Thread
 * ------------------------------------------------------------------------- - Managed by the JVM instead of the
 * operating system. - Extremely lightweight. - Very cheap to create. - Millions of virtual threads can exist
 * simultaneously. - Ideal for I/O-bound applications such as: • Web servers • REST APIs • Database operations • Network
 * communication
 * <p>
 * Virtual Threads simplify concurrent programming because developers can often write blocking code without sacrificing
 * scalability.
 */
public class ExecutorServiceVirtualThreadsExample {

  public static void main(String[] args) {

    /*
     * Creates an ExecutorService that creates
     * ONE NEW Virtual Thread for every submitted task.
     *
     * Unlike fixed thread pools, there is no worker thread reuse.
     * Every submitted task gets its own Virtual Thread.
     *
     * Since ExecutorService implements AutoCloseable,
     * try-with-resources automatically closes the executor.
     */
    try (ExecutorService executor =
        Executors.newVirtualThreadPerTaskExecutor()) {

      /*
       * Submit a Runnable task.
       *
       * Runnable:
       * - performs work
       * - returns no value
       */
      executor.submit(() -> {

        System.out.println(
            Thread.currentThread().getName()
                + " is executing a Runnable task."
        );

      });

      /*
       * Callable:
       * - performs work
       * - returns a value
       * - may throw checked exceptions
       */
      Callable<String> callableTask = () -> {

        System.out.println(
            Thread.currentThread().getName()
                + " is executing a Callable task."
        );

        // Simulate a small amount of work.
        Thread.sleep(1000);

        return "Result returned from Callable.";
      };

      /*
       * Submit the Callable.
       *
       * submit() immediately returns a Future.
       * The computation continues asynchronously.
       */
      Future<String> future = executor.submit(callableTask);

      try {

        /*
         * Future.get() is a BLOCKING operation.
         *
         * If the Callable has not yet completed,
         * the current thread waits here.
         */
        String result = future.get();

        System.out.println("Callable Result : " + result);

      } catch (InterruptedException e) {

        /*
         * Restore the interrupt status so that
         * higher-level code can detect the interruption.
         */
        Thread.currentThread().interrupt();

      } catch (ExecutionException e) {

        /*
         * Wraps any exception thrown by the Callable.
         */
        e.printStackTrace();
      }

      /*
       * No need to call:
       *
       * executor.shutdown();
       *
       * executor.awaitTermination(...);
       *
       * The try-with-resources block automatically closes
       * the ExecutorService when execution leaves this block.
       */
    }

    System.out.println("Main thread has finished.");
  }
}