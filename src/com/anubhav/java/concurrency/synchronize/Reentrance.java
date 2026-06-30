package com.anubhav.java.concurrency.synchronize;

public class Reentrance {

  private int count = 0;

  public synchronized void increment() {
    this.count++;
  }

  public synchronized int incAndGet() {
    increment();
    return this.count;
  }
}
