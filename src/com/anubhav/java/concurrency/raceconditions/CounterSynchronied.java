package com.anubhav.java.concurrency.raceconditions;

public class CounterSynchronied {

  private long count = 0;

  public long incAndGet() {
    synchronized (this) {
      this.count++;
      return count;
    }
  }

  public long get() {
    synchronized (this) {
      return this.count;
    }
  }
}
