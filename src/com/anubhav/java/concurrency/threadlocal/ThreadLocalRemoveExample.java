package com.anubhav.java.concurrency.threadlocal;

public class ThreadLocalRemoveExample {

  public static void main(String[] args) {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    Thread thread1 = new Thread(() -> {
      threadLocal.set("Thread1");
      String val = threadLocal.get();
      System.out.println(val);
      threadLocal.remove();
      val = threadLocal.get();
      System.out.println(val);
    });
    Thread thread2 = new Thread(() -> {
      threadLocal.set("Thread2");
      String val = threadLocal.get();
      System.out.println(val);
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      val = threadLocal.get();
      System.out.println(val);
      threadLocal.remove();
      val = threadLocal.get();
      System.out.println(val);
    });
    thread1.start();
    thread2.start();
  }
}
