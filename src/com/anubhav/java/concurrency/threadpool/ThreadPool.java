package com.anubhav.java.concurrency.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * A simple custom Thread Pool implementation.
 * <p>
 * This class demonstrates the core ideas behind Java's ExecutorService.
 * <p>
 * Components:
 * <p>
 * Task Queue Stores submitted Runnable tasks.
 * <p>
 * Worker Threads Continuously fetch tasks from the queue and execute them.
 * <p>
 * ThreadPool Accepts new tasks and manages workers.
 * <p>
 * NOTE: This implementation is intentionally simplified for educational purposes.
 */
public class ThreadPool {

  /*
   * Queue holding submitted tasks.
   *
   * BlockingQueue provides thread-safe
   * producer-consumer behavior.
   */
  private final BlockingQueue<Runnable> taskQueue;

  /*
   * Worker threads.
   */
  private final List<PoolThreadRunnable> workers =
      new ArrayList<>();

  /*
   * Indicates whether the pool has stopped.
   */
  private boolean isStopped = false;

  /**
   * Creates a ThreadPool.
   *
   * @param numberOfThreads Number of worker threads.
   * @param maxTasks        Maximum queue capacity.
   */
  public ThreadPool(int numberOfThreads,
      int maxTasks) {

    taskQueue = new ArrayBlockingQueue<>(maxTasks);

    /*
     * Create worker objects.
     */
    for (int i = 0; i < numberOfThreads; i++) {

      PoolThreadRunnable worker =
          new PoolThreadRunnable(taskQueue);

      workers.add(worker);
    }

    /*
     * Start all worker threads.
     */
    for (PoolThreadRunnable worker : workers) {

      Thread thread = new Thread(worker);

      thread.setName("Worker-" + thread.getId());

      thread.start();
    }
  }

  /**
   * Submit a task to the pool.
   * <p>
   * If the queue is full, put() blocks until space becomes available.
   */
  public synchronized void execute(Runnable task)
      throws InterruptedException {

    if (isStopped) {

      throw new IllegalStateException(
          "ThreadPool has already been stopped."
      );
    }

    taskQueue.put(task);
  }

  /**
   * Stops the thread pool.
   * <p>
   * Running tasks are allowed to complete.
   * <p>
   * Waiting worker threads are interrupted.
   */
  public synchronized void stop() {

    isStopped = true;

    for (PoolThreadRunnable worker : workers) {

      worker.doStop();
    }
  }

  /**
   * Wait until all queued tasks have been taken.
   * <p>
   * NOTE: This only checks whether the queue is empty.
   * <p>
   * A worker thread may still be executing the last task.
   * <p>
   * Real ExecutorService implementations provide much stronger guarantees.
   */
  public void waitUntilAllTasksFinished() {

    while (!taskQueue.isEmpty()) {

      try {

        Thread.sleep(10);

      } catch (InterruptedException e) {

        Thread.currentThread().interrupt();

        break;
      }
    }
  }
}