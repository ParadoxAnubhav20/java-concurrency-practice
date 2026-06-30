package com.anubhav.java.concurrency.synchronize;

public class StaticSynchronizedExchanger {

  protected static Object object = null;

  public synchronized void setObject(Object obj) {
    object = obj;
  }

  public synchronized Object getObject() {
    return object;
  }

  public void setObj(Object obj) {
    synchronized (StaticSynchronizedExchanger.class) {
      object = obj;
    }
  }

  public static Object getObj() {
    synchronized (StaticSynchronizedExchanger.class) {
      return object;
    }
  }

}
