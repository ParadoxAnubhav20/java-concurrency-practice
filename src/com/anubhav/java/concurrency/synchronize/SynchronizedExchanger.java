package com.anubhav.java.concurrency.synchronize;

public class SynchronizedExchanger {

  protected Object object = null;

  public synchronized void setObject(Object obj) {
    this.object = obj;
  }

  public synchronized Object getObject() {
    return this.object;
  }

  public void setObj(Object obj) {
    synchronized (this) {
      this.object = obj;
    }
  }

}
