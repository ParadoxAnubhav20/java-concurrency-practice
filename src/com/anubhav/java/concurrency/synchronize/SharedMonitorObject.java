package com.anubhav.java.concurrency.synchronize;

public class SharedMonitorObject {

  private Object monitor = null;
  private int counter = 0;

  public SharedMonitorObject(Object monitor) {
    if (monitor == null) {
      throw new IllegalArgumentException("Monitor Object Cannot Be Null");
    }
    this.monitor = monitor;
  }

  public void incCounter() {
    synchronized (this.monitor) {
      this.counter++;
    }
  }
}
