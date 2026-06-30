package com.anubhav.java.concurrency.volatileex;

public class Counter {

  private volatile int count = 0;

  public boolean inc() {
    if (this.count == 0) {
      return false;
    }
    this.count++; //not atomic
    return true;
  }

  public int get() {
    return this.count;
  }

}
