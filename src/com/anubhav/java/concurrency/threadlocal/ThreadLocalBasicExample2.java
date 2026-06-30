package com.anubhav.java.concurrency.threadlocal;

public class ThreadLocalBasicExample2 {

  public static void main(String[] args) {
    ThreadLocal<MyObject> threadLocal1 = ThreadLocal.withInitial(MyObject::new);
    ThreadLocal<MyObject> threadLocal2 = ThreadLocal.withInitial(MyObject::new);
    Thread thread1 = new Thread(() -> {
      System.out.println("threadlocal1 : " + threadLocal1.get());
      System.out.println("threadlocal2 : " + threadLocal2.get());
    });
    Thread thread2 = new Thread(() -> {
      System.out.println("threadlocal1 : " + threadLocal1.get());
      System.out.println("threadlocal2 : " + threadLocal2.get());
    });
    thread1.start();
    thread2.start();

  }
}
