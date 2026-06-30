package com.anubhav.java.concurrency.synchronize;

public class SyncCounter {

  private long count = 0;

  public synchronized void incCount() {
    this.count++;
  }

  public long getCount() {
    return this.count;
  }
}
