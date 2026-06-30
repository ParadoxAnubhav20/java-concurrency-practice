package com.anubhav.java.concurrency.raceconditions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RaceConditionsExample5 {

  public static void main(String[] args) {
    Map<String, String> sharedMap = new ConcurrentHashMap<String, String>();
    Thread thread1 = new Thread(getRunnable(sharedMap));
    Thread thread2 = new Thread(getRunnable(sharedMap));
    thread1.start();
    thread2.start();
  }

  private static Runnable getRunnable(Map<String, String> sharedMap) {
    return () -> {
      for (int i = 0; i < 1_000_000; i++) {
        if (sharedMap.containsKey("key")) {
          String val = sharedMap.remove("key");
          if (val == null) {
            System.out.println("Iteration: " + i + " : value for 'key was null");
          }
        }
        else {
          sharedMap.put("key", "value");
        }
      }
    };
  }

}
