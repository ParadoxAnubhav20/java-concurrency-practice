package com.anubhav.java.concurrency.threadlocal;

public class ThreadLocalLazyInitExample {

  public static void main(String[] args) {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    String val = threadLocal.get();
    if (val == null) {
      threadLocal.set("Lazily Setting Value");
      val = threadLocal.get();
    }
    System.out.println(val);
  }
}
