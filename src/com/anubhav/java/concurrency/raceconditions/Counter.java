package com.anubhav.java.concurrency.raceconditions;

public class Counter {

  private long count = 0;

  public long incAndGet() {
    this.count++;
    return count;
  }

  public long get() {
    return this.count;
  }
}
