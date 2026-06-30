package com.anubhav.java.concurrency.concurrentmap;

import java.util.Map;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Demonstrates the basic usage of ConcurrentMap.
 * <p>
 * ConcurrentMap is a thread-safe extension of the Map interface.
 * <p>
 * The most common implementation is ConcurrentHashMap.
 * <p>
 * Unlike HashMap, ConcurrentHashMap allows multiple threads to safely read and modify the map concurrently without
 * external synchronization.
 */
public class ConcurrentMapExample {

  public static void main(String[] args) {

    /*
     * -------------------------------------------------------
     * Different Map Implementations
     * -------------------------------------------------------
     */

    /*
     * HashMap
     *
     * - NOT thread-safe.
     * - Fastest in single-threaded applications.
     */
    Map<String, String> hashMap = new HashMap<>();

    /*
     * Hashtable
     *
     * - Thread-safe.
     * - Synchronizes every method.
     * - Mostly considered legacy.
     */
    Map<String, String> hashtable = new Hashtable<>();

    /*
     * ConcurrentHashMap
     *
     * - Thread-safe.
     * - Allows high concurrency.
     * - Preferred over Hashtable.
     */
    Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();

    /*
     * ConcurrentMap is the interface.
     *
     * ConcurrentHashMap is the implementation.
     */
    ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<>();

    /*
     * -------------------------------------------------------
     * Basic Operations
     * -------------------------------------------------------
     */

    concurrentMap.put("Java", "21");

    String version = concurrentMap.get("Java");

    System.out.println("Java Version : " + version);

    concurrentMap.remove("Java");

    /*
     * -------------------------------------------------------
     * Atomic Operations
     * -------------------------------------------------------
     */

    /*
     * Inserts the key only if it does NOT already exist.
     *
     * Entire operation is atomic.
     */
    concurrentMap.putIfAbsent("Spring", "Boot");

    /*
     * Replaces the value only if the key exists.
     */
    concurrentMap.replace("Spring", "Framework");

    /*
     * Removes the mapping only if BOTH
     * key and value match.
     */
    concurrentMap.remove("Spring", "Framework");

    /*
     * -------------------------------------------------------
     * Slipped Condition
     * -------------------------------------------------------
     *
     * The following code is NOT atomic.
     *
     * Another thread may modify the map
     * between any of these operations.
     */

    if (concurrentMap.containsKey("Key")) {

      String value = concurrentMap.get("Key");

      concurrentMap.remove("Key");

      System.out.println(value);
    }

    /*
     * Better approach:
     *
     * remove(key)
     * itself is atomic.
     */

    String removedValue = concurrentMap.remove("AnotherKey");

    if (removedValue != null) {

      System.out.println("Removed : " + removedValue);
    }

    /*
     * -------------------------------------------------------
     * computeIfAbsent()
     * -------------------------------------------------------
     *
     * Computes a value only if
     * the key is missing.
     */

    concurrentMap.computeIfAbsent(

        "Database",

        key -> "PostgreSQL"

    );

    /*
     * -------------------------------------------------------
     * computeIfPresent()
     * -------------------------------------------------------
     *
     * Updates the value only if
     * the key already exists.
     */

    concurrentMap.computeIfPresent(

        "Database",

        (key, value) -> value.toUpperCase()

    );

    /*
     * Print final contents.
     */
    System.out.println(concurrentMap);
  }
}