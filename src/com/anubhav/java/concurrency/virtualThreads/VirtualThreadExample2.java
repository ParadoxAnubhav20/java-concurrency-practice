package com.anubhav.java.concurrency.virtualThreads;

import java.util.ArrayList;
import java.util.List;

public class VirtualThreadExample2 {

  public static void main(String[] args) {
    List<Thread> vThreads = new ArrayList<Thread>();
    int vThreadCount = 100_000;
    for (int i = 0; i < vThreadCount; i++) {
      int vThreadIndex = i;
      Thread vThread = Thread.ofVirtual().start(() -> {
        int res = 1;
        for (int j = 0; j < 10; j++) {
          res *= (j + 1);
        }
        System.out.println("Result[" + vThreadIndex + "] = " + res);
      });
      vThreads.add(vThread);
    }

    for (int i = 0; i < vThreadCount; i++) {
      try {
        vThreads.get(i).join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }


  }
}
