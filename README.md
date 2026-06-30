# Java Concurrency & Multithreading

> A comprehensive collection of Java concurrency examples, explanations, and implementations ranging from basic thread creation to custom thread pools and modern virtual threads (Project Loom).

![Java](https://img.shields.io/badge/Java-21+-orange)
![License](https://img.shields.io/badge/License-MIT-green)
![Status](https://img.shields.io/badge/Status-In%20Progress-blue)
![Focus](https://img.shields.io/badge/Focus-Java%20Concurrency-red)

---

# Table of Contents

- [Overview](#overview)
- [Learning Objectives](#learning-objectives)
- [Repository Structure](#repository-structure)
- [Why Learn Concurrency?](#why-learn-concurrency)
- [Process vs Thread](#process-vs-thread)
- [Concurrency vs Parallelism](#concurrency-vs-parallelism)
- [CPU Scheduling](#cpu-scheduling)
- [Thread Lifecycle](#thread-lifecycle)
- [Java Memory Model (JMM)](#java-memory-model-jmm)
- [Shared Memory vs Separate Memory](#shared-memory-vs-separate-memory)
- [What You'll Learn](#what-youll-learn)
- [Race Conditions, Synchronization & Locks](#race-conditions-synchronization--locks)
- [Executor Framework, Thread Pools, Runnable, Callable & Future](#executor-framework-thread-pools-runnable-callable--future)

---

# Overview

Modern software almost never executes just one task at a time. It typically needs to:

- Handle thousands of users.
- Process network requests.
- Read and write databases.
- Perform file operations.
- Execute background jobs.
- Communicate with external services.
- Perform asynchronous computations.

To efficiently utilize CPU resources and improve responsiveness, Java provides a powerful concurrency framework.

This repository demonstrates Java concurrency from the ground up, beginning with basic synchronization concepts and progressing toward advanced topics such as:

- Locks
- Deadlocks
- Thread pools
- `ExecutorService`
- `Future`
- `Callable`
- Virtual threads (Project Loom)
- Custom thread-pool implementation

The examples are intentionally small, heavily commented, and designed for learning and interview preparation rather than production use.

---

# Learning Objectives

After completing this repository you should understand:

✅ How Java threads work  
✅ How threads communicate  
✅ Shared memory in Java  
✅ Synchronization  
✅ Race conditions  
✅ Critical sections  
✅ Locks  
✅ `ReentrantLock`  
✅ Deadlocks  
✅ `ExecutorService`  
✅ Thread pools  
✅ `Runnable`  
✅ `Callable`  
✅ `Future`  
✅ Blocking operations  
✅ Executor lifecycle  
✅ Virtual threads  
✅ Custom thread-pool implementation

---

# Why Learn Concurrency?

Imagine a web server where thousands of users may request pages simultaneously. If only one request could be processed at a time, every other user would have to wait, and the server would quickly become unusable.

```text
User 1
↓

// Server executes request

↓
User 2 waits
↓
User 3 waits
↓
User 4 waits
```

Instead, multiple requests are processed concurrently:

```text
User 1 ─┐
User 2 ─┤
User 3 ─┼────► Server
User 4 ─┘
```

Almost every modern application relies on concurrency, including:

- Web servers
- Database servers
- Game engines
- Android applications
- Desktop applications
- Operating systems
- Cloud services
- Distributed systems

Concurrency is one of the most frequently tested topics in Java interviews, especially for backend and system-design roles.

---

# Process vs Thread

## Process

A **process** is an independent program executing in memory.

Examples:

- Chrome
- IntelliJ IDEA
- Spotify
- Discord

Each process has:

- Separate memory
- Separate heap
- Separate stack
- Separate resources

Example:

```text
Operating System
│
├── Chrome Process
├── Spotify Process
├── IntelliJ Process
└── Discord Process
```

Processes are isolated from one another. One process cannot directly access another process's memory.

---

## Thread

A **thread** is a lightweight unit of execution inside a process. One process may contain many threads.

Example:

```text
Chrome Process
│
├── UI Thread
├── Network Thread
├── Rendering Thread
├── JavaScript Thread
└── Audio Thread
```

Threads share:

- Heap
- Objects
- Static variables

but each thread has its own:

- Stack
- Program counter
- Local variables

---

## Process vs Thread Summary

| Process              | Thread                       |
|----------------------|------------------------------|
| Heavyweight          | Lightweight                  |
| Independent          | Exists inside a process      |
| Separate memory      | Shared heap                  |
| Expensive to create  | Cheaper to create            |
| Slow context switch  | Faster context switch        |

---

# Concurrency vs Parallelism

These terms are often confused, but they are **not** the same.

## Concurrency

Concurrency means:

> Multiple tasks make progress during overlapping periods of time.

Only one task may actually execute at a given instant on a single CPU core, but the CPU switches between tasks frequently.

Example:

```text
Task A
↓↓↓
Task B
↓↓↓
Task A
↓↓↓
Task B
```

The CPU rapidly switches between tasks, and to the user it appears they are running simultaneously.

---

## Parallelism

Parallelism means:

> Multiple tasks literally execute at the same time.

Example:

```text
Core 1
Task A
------------
Core 2
Task B
```

Parallelism requires multiple CPU cores.

---

## Visual Comparison

Concurrency:

```text
Task A
████
Task B
    ████
Task C
        ████
```

Parallelism:

```text
Core 1
████████
Core 2
████████
Core 3
████████
```

---

# CPU Scheduling

The operating-system scheduler decides:

- Which thread executes.
- When it executes.
- For how long it executes.

This process is called **context switching**.

Example:

```text
CPU
↓
Thread A
↓
Thread B
↓
Thread C
↓
Thread A
↓
Thread B
```

Because of extremely fast context switching, humans perceive multiple tasks as executing simultaneously.

---

# Thread Lifecycle

A Java thread passes through several states:

```text
NEW
↓
RUNNABLE
↓
RUNNING
↓
BLOCKED
↓
WAITING
↓
TIMED_WAITING
↓
TERMINATED
```

### NEW

The thread object has been created but not yet started.

```java
Thread t = new Thread(() -> {
    // task
});
```

---

### RUNNABLE

The thread has been started and is eligible to run.

```java
t.start();
```

The scheduler decides when it actually runs.

---

### RUNNING

The CPU is currently executing the thread's instructions.

---

### BLOCKED

The thread is waiting to acquire a monitor lock.

Example:

```java
synchronized (lock) {
    // critical section
}
```

Another thread already owns the lock.

---

### WAITING

The thread is waiting indefinitely for another thread to perform a specific action.

Examples:

```java
Object.wait();
Thread.join();
LockSupport.park();
```

---

### TIMED_WAITING

The thread is waiting for a specified amount of time.

Examples:

```java
Thread.sleep(1000);
future.get(timeout, unit);
await(timeout, unit);
```

---

### TERMINATED

The thread has completed execution and will not run again.

---

# Java Memory Model (JMM)

The **Java Memory Model (JMM)** defines how threads interact with memory. It specifies:

- Visibility (when changes made by one thread become visible to others).
- Ordering (rules about the ordering of reads and writes).
- Atomicity (which operations are indivisible).

Without the JMM, multithreaded programs would behave unpredictably across different CPUs and JVM implementations.

---

## Main Memory

Main memory is shared by every thread. It contains:

- Objects
- Static variables
- Arrays

```text
Main Memory
│
├── Object A
├── Object B
├── Counter
└── Cache
```

---

## Working Memory

Each thread has its own working memory (often modeled as a cache or local copy of variables):

```text
Main Memory
      ▲
      │
──────────────
Thread A Cache
Thread B Cache
```

Threads do not always read directly from main memory. Instead, they often work with cached copies.

---

## Why Synchronization Matters

Suppose we have:

```text
count = 0
```

Thread A:

```java
count++;
```

Thread B:

```java
count++;
```

Expected result:

```text
count = 2
```

Actual result may be:

```text
count = 1
```

Why?

Because `count++` is **not an atomic operation**. It consists of three separate steps:

```text
Read count
↓
Increment value
↓
Write updated value
```

If both threads read the old value before either writes back the new value, one update is lost. This is a classic **lost-update problem**, a type of **race condition**.

Synchronization ensures:

- Correct visibility.
- Correct ordering.
- Atomic execution where required.

---

# Shared Memory vs Separate Memory

This repository contains examples demonstrating both shared and separate memory scenarios.

### Shared `Runnable`

```text
Thread 1
        │
        ▼
Shared Runnable
count
↓
Shared Object
```

Both threads modify the same variables, so synchronization is required.

---

### Separate `Runnable`

```text
Runnable A
↓
Thread A
----------------
Runnable B
↓
Thread B
```

Each thread owns its own `Runnable` instance. The `count` variable is **not** shared, so race conditions on that variable cannot occur.

---

# What You'll Learn

This repository gradually progresses through increasingly advanced topics:

1. Thread creation
2. Shared memory
3. Synchronization
4. Intrinsic locks (`synchronized`)
5. Explicit locks (`Lock`, `ReentrantLock`)
6. Deadlocks
7. Thread pools
8. `ExecutorService`
9. `Future`
10. `Callable`
11. Virtual threads
12. Custom thread-pool implementation

Each topic contains:

- Fully documented source code.
- Step-by-step explanations.
- Practical examples.
- Best practices.
- Common pitfalls.
- Interview-oriented notes.

---

# Race Conditions, Synchronization & Locks

---

## Race Condition

A **race condition** occurs when two or more threads simultaneously access and modify the same shared data, and the final result depends on the order in which the threads execute.

Since thread scheduling is controlled by the operating system and JVM scheduler, the execution order is unpredictable.

### Example

Suppose we have the following shared variable:

```java
int count = 0;
```

Thread A executes:

```java
count++;
```

Thread B also executes:

```java
count++;
```

At first glance, we might expect:

```text
count = 2
```

However, the actual result may be:

```text
count = 1
```

### Why?

Because `count++` is **not an atomic operation**. It consists of three separate operations:

```text
Read count
↓
Increment value
↓
Write updated value
```

Possible interleaving:

```text
Initial Value
count = 0
-------------------------
Thread A reads 0
Thread B reads 0
Thread A increments → 1
Thread B increments → 1
Thread A writes 1
Thread B writes 1
Final Value = 1
```

One increment has been lost. This is known as a **lost-update problem**.

---

## Atomic Operations

An operation is **atomic** if it is completed as one indivisible unit. No other thread can observe the operation halfway through.

Examples:

Atomic:

```java
AtomicInteger.incrementAndGet();
```

Not atomic:

```java
count++;
```

Not atomic:

```java
count = count + 1;
```

---

## Shared Mutable State

Concurrency problems occur only when multiple threads access **shared mutable state**. There are three requirements:

- Shared between threads.
- Mutable (modifiable).
- Accessed without proper synchronization.

Example:

```text
                Counter
                count
               ▲     ▲
               │     │
          Thread A Thread B
```

If `count` is modified by multiple threads simultaneously, race conditions become possible.

---

## Critical Section

A **critical section** is any block of code that accesses shared mutable data. Only one thread should execute a critical section at a time.

Example:

```java
count++;
```

Although this appears to be a single statement, it modifies shared state and therefore is a critical section.

Visual representation:

```text
Thread A
Acquire Lock
↓
Critical Section
↓
Release Lock
------------------------
Thread B
Wait
↓
Acquire Lock
↓
Critical Section
↓
Release Lock
```

---

## Mutual Exclusion

**Mutual exclusion (mutex)** means that only one thread is allowed to enter a critical section at any given time. This prevents race conditions.

Example:

```text
Shared Counter
        ▲
        │
Lock
        ▲
        │
Thread A
Thread B
Thread C
```

Only one thread can acquire the lock at a time. The remaining threads must wait.

---

## Thread Safety

A class is **thread-safe** if it behaves correctly when accessed concurrently by multiple threads.

Example:

```java
public synchronized void increment() {
    count++;
}
```

Since only one thread may execute this method at a time for a given instance, concurrent access is safe.

Examples of thread-safe classes:

- `ConcurrentHashMap`
- `AtomicInteger`
- `CopyOnWriteArrayList`
- `BlockingQueue`

Examples of non-thread-safe classes:

- `ArrayList`
- `HashMap`
- `HashSet`
- `StringBuilder`

---

## Synchronization

Java provides synchronization mechanisms to coordinate access to shared resources. Synchronization guarantees:

- Mutual exclusion.
- Memory visibility.
- Happens-before relationships.

Without synchronization:

```text
Thread A
updates value
↓
Thread B
may never see the update
```

With synchronization:

```text
Thread A
updates value
↓
Unlock
↓
Lock
↓
Thread B
reads latest value
```

---

## The `synchronized` Keyword

The `synchronized` keyword is Java's built-in locking mechanism. It automatically:

- Acquires a monitor.
- Executes the critical section.
- Releases the monitor.

Example:

```java
public synchronized void increment() {
    count++;
}
```

Conceptually equivalent to:

```text
Acquire monitor
↓
Execute code
↓
Release monitor
```

---

## Intrinsic Lock (Monitor)

Every Java object has an **intrinsic lock**, also called a **monitor**.

Example:

```java
Object lock = new Object();
```

The object automatically owns a monitor. When we write:

```java
synchronized (lock) {
    // critical section
}
```

the JVM:

```text
Acquire object's monitor
↓
Execute block
↓
Release monitor
```

No explicit `lock()` or `unlock()` calls are required.

---

## Synchronized Instance Methods

Example:

```java
public synchronized void increment() {
    count++;
}
```

For an instance method, the monitor belongs to `this`. This is equivalent to:

```java
public void increment() {
    synchronized (this) {
        count++;
    }
}
```

Only one thread may execute any synchronized instance method on the **same object** at a time.

---

## Static Synchronized Methods

Example:

```java
public static synchronized void print() {
    // ...
}
```

The monitor is **not** `this`. Instead, it belongs to the class object, e.g. `MyClass.class`.

Equivalent to:

```java
public static void print() {
    synchronized (MyClass.class) {
        // ...
    }
}
```

All instances of the class share the same lock for static synchronized methods.

---

## Synchronized Block

Instead of synchronizing an entire method:

```java
public void increment() {
    synchronized (this) {
        count++;
    }
}
```

Only the critical section is protected. Advantages include:

- Smaller lock scope.
- Better performance.
- Easier to reason about.

---

## `Lock` Interface

Java also provides explicit locking through the `Lock` interface:

```java
Lock lock = new ReentrantLock();
```

Unlike `synchronized`, developers manually acquire and release the lock.

Example:

```java
lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}
```

The `finally` block is essential; without it, an exception could leave the lock permanently held, causing deadlocks or starvation.

---

## `ReentrantLock`

`ReentrantLock` is the most commonly used implementation of the `Lock` interface.

"Reentrant" means:

> The thread that already owns the lock may acquire it again without blocking itself.

Example:

```java
lock.lock();   // hold count = 1
lock.lock();   // hold count = 2
lock.unlock(); // hold count = 1
lock.unlock(); // hold count = 0
```

Each `lock()` call must have a matching `unlock()`.

---

## Why Use `ReentrantLock` Instead of `synchronized`?

`ReentrantLock` offers several features unavailable with `synchronized`:

| Feature                         | `synchronized` | `ReentrantLock` |
|---------------------------------|----------------|-----------------|
| Automatic lock release          | ✅              | ❌               |
| Timed lock acquisition          | ❌              | ✅               |
| Interruptible lock acquisition  | ❌              | ✅               |
| Fair scheduling                 | ❌              | ✅               |
| Multiple condition variables    | ❌              | ✅               |
| Explicit `unlock()`             | ❌              | ✅               |

---

## Fair vs Non-Fair Locks

By default:

```java
Lock lock = new ReentrantLock();
```

creates a **non-fair** lock. The JVM may allow newly arriving threads to "jump ahead" of threads that have been waiting longer.

A fair lock is created as:

```java
Lock fairLock = new ReentrantLock(true);
```

Threads generally acquire the lock in the order they requested it. Fair locks improve predictability but usually reduce throughput.

---

## Deadlock

A **deadlock** occurs when two or more threads wait forever for resources held by each other.

Example:

```text
Thread A
owns Lock-1
waiting for Lock-2
-------------------------
Thread B
owns Lock-2
waiting for Lock-1
```

Neither thread can continue; both wait forever.

---

## The Four Coffman Conditions

Deadlocks occur only if **all four** of the following conditions hold:

1. **Mutual exclusion** – resources cannot be shared; only one thread can own a resource at a time.
2. **Hold and wait** – a thread holds one resource while waiting for another.
3. **No preemption** – resources cannot be forcibly taken away; only the owning thread may release them.
4. **Circular wait** – a cycle exists where each thread waits for a resource held by the next thread in the cycle.

Example of circular wait:

```text
Thread A
↓
Lock-2
↓
Thread B
↓
Lock-1
↓
Thread A
```

Breaking any one of these four conditions prevents deadlocks.

---

## Avoiding Deadlocks

Common strategies include:

### 1. Always Acquire Locks in the Same Order

Good:

```text
Thread A
Lock-1
↓
Lock-2
---------------------
Thread B
Lock-1
↓
Lock-2
```

No circular wait occurs.

---

### 2. Use `tryLock()`

Instead of waiting forever:

```java
if (lock.tryLock()) {
    try {
        // critical section
    } finally {
        lock.unlock();
    }
} else {
    // could not acquire lock, back off
}
```

The thread can back off or retry later if the lock is unavailable.

---

### 3. Reduce Lock Scope

Keep critical sections as short as possible. Less time holding locks means fewer opportunities for deadlocks and better throughput.

---

## Best Practices

- Keep critical sections small.
- Never forget to release a lock.
- Always use `try`/`finally` with `Lock`.
- Avoid nested locks unless necessary.
- Acquire multiple locks in a consistent global order.
- Prefer high-level concurrency utilities (`ExecutorService`, `BlockingQueue`, `ConcurrentHashMap`) over manual synchronization when possible.
- Minimize shared mutable state.
- Favor immutable objects where practical.

---

## Common Interview Questions (Locks & Synchronization)

### Why is `count++` not thread-safe?

Because it consists of three operations (read, modify, write) and another thread may interleave between them, causing a lost update.

---

### Why should `unlock()` always be inside a `finally` block?

To guarantee that the lock is released even if an exception occurs inside the critical section.

---

### What is a monitor?

A monitor is the intrinsic lock associated with every Java object, used by `synchronized`.

---

### Difference between `synchronized` and `Lock`?

- `synchronized` is built into the language and automatically releases the monitor when the block/method exits.
- `Lock` is part of `java.util.concurrent.locks`, requires explicit `lock()`/`unlock()`, and provides additional capabilities such as `tryLock()`, fairness, and interruptible lock acquisition.

---

### What is reentrancy?

The ability of the same thread to acquire the same lock multiple times without deadlocking itself.

---

### What causes deadlocks?

Deadlocks require all four Coffman conditions: mutual exclusion, hold and wait, no preemption, and circular wait.

---

# Executor Framework, Thread Pools, Runnable, Callable & Future

---

## Why Was the Executor Framework Introduced?

Before Java 5, developers commonly created threads manually:

```java
Thread thread = new Thread(() -> {
    System.out.println("Running...");
});
thread.start();
```

While this works for small applications, it becomes inefficient as the number of tasks grows. Imagine a web server receiving **10,000 HTTP requests** and creating one thread per request:

```text
10,000 Requests
↓
10,000 Threads
↓
Huge Memory Usage
↓
Heavy Context Switching
↓
Poor Performance
```

Creating threads is expensive because every thread requires:

- Stack memory.
- JVM bookkeeping.
- Operating-system resources.
- Scheduling overhead.

Instead of constantly creating and destroying threads, Java introduced the **Executor Framework**.

---

## What Is the Executor Framework?

The Executor Framework is a high-level API introduced in Java 5 (`java.util.concurrent`) for managing asynchronous task execution.

Instead of asking:

> "Create a thread."

You ask:

> "Execute this task."

The framework decides:

- Which thread executes it.
- When it executes.
- Whether an existing worker thread can be reused.

This separation makes applications more scalable and easier to maintain.

---

## Executor Framework Architecture

```text
Application
        │
submit(task)
        │
        ▼
ExecutorService
        │
        ▼
Task Queue
        │
        ▼
Worker Threads
        │
        ▼
Task Executes
```

Instead of creating threads directly, tasks are submitted to an `ExecutorService`. Worker threads continuously fetch tasks from the queue and execute them.

---

## `Executor` Interface

The root interface is:

```java
public interface Executor {
    void execute(Runnable command);
}
```

Purpose:

- Accept a task.
- Execute it sometime in the future.

Notice that `Executor` does **not** define:

- `shutdown()`.
- `Future`.
- `submit()`.
- Thread-pool management.

Those belong to `ExecutorService`.

---

## `ExecutorService`

`ExecutorService` extends `Executor` and adds lifecycle management and task monitoring.

Important methods include:

```java
void execute(Runnable command);
<T> Future<T> submit(Callable<T> task);
Future<?> submit(Runnable task);
void shutdown();
List<Runnable> shutdownNow();
boolean awaitTermination(long timeout, TimeUnit unit);
<T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks);
<T> T invokeAny(Collection<? extends Callable<T>> tasks);
```

Most real-world Java applications use `ExecutorService`.

---

## Executor vs ExecutorService

| Aspect               | `Executor`             | `ExecutorService`            |
|----------------------|------------------------|------------------------------|
| Core method          | `execute()`           | `execute()`, `submit()`, etc |
| Shutdown support     | No                    | Yes                          |
| Future support       | No                    | Yes                          |
| Lifecycle management | No                    | Yes                          |

---

## Creating an `ExecutorService`

The easiest way is through the `Executors` factory class:

```java
ExecutorService executor = Executors.newFixedThreadPool(4);
```

Here, the pool size is 4. Only four worker threads are created, and tasks exceeding the pool size wait in an internal queue.

---

## Thread Pool

A **thread pool** is a collection of reusable worker threads.

Instead of:

```text
Task
↓
Create Thread
↓
Destroy Thread
↓
Create Thread
↓
Destroy Thread
```

A pool behaves like:

```text
Worker-1
Task 1
↓
Task 4
↓
Task 8
----------------
Worker-2
Task 2
↓
Task 5
↓
Task 9
```

Threads are reused instead of constantly recreated.

---

## Benefits of Thread Pools

Thread pools provide several advantages:

- Reduced thread-creation overhead.
- Better CPU utilization.
- Lower memory consumption.
- Improved scalability.
- Centralized thread management.
- Ability to limit concurrent execution.

Almost every server-side Java framework uses thread pools internally, including:

- Spring Boot.
- Tomcat.
- Jetty.
- Netty.
- Kafka.
- gRPC.

---

## Common Thread-Pool Types

### Fixed Thread Pool

```java
ExecutorService executor = Executors.newFixedThreadPool(4);
```

Characteristics:

- Fixed number of worker threads.
- Extra tasks wait in a queue.
- Most commonly used pool.

Example:

```text
Pool Size = 3
Task-1
Task-2
Task-3
Task-4
Task-5
↓
Worker-1
Worker-2
Worker-3
```

---

### Single-Thread Executor

```java
ExecutorService executor = Executors.newSingleThreadExecutor();
```

Characteristics:

- Exactly one worker thread.
- Tasks execute sequentially.
- Preserves submission order.

Example:

```text
Task-1
↓
Task-2
↓
Task-3
```

No two tasks execute simultaneously.

---

### Cached Thread Pool

```java
ExecutorService executor = Executors.newCachedThreadPool();
```

Characteristics:

- Creates new threads as needed.
- Reuses idle threads.
- No fixed upper limit on thread count.

Best suited for many short-lived tasks. Be careful: large workloads may create a huge number of threads.

---

### Scheduled Thread Pool

```java
ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
```

Used for:

- Delayed tasks.
- Periodic tasks.
- Timers.
- Background jobs.

Example:

```text
Run every 10 seconds
↓
Collect Metrics
↓
Repeat
```

---

## `Runnable`

A `Runnable` represents a unit of work.

Interface:

```java
public interface Runnable {
    void run();
}
```

Characteristics:

- Returns nothing (`void`).
- Cannot throw checked exceptions.
- Used for tasks that simply perform work.

Example:

```java
Runnable task = () -> {
    System.out.println("Executing...");
};
```

---

## `execute()`

A `Runnable` can be submitted using:

```java
executor.execute(task);
```

Characteristics:

- Fire-and-forget.
- Returns nothing.
- No `Future` object.

Execution flow:

```text
Runnable
↓
execute()
↓
Thread Pool
↓
Worker Thread
↓
run()
```

---

## `submit(Runnable)`

A `Runnable` may also be submitted using:

```java
Future<?> future = executor.submit(task);
```

Difference:

Unlike `execute()`, `submit()` returns a **`Future`**. The task is still a `Runnable`, so it does not produce a result. Calling:

```java
future.get();
```

returns `null` after successful completion.

---

## `Callable`

`Callable` is similar to `Runnable`, but it can return a value and throw checked exceptions.

Interface:

```java
public interface Callable<V> {
    V call() throws Exception;
}
```

Characteristics:

- Returns a value.
- Can throw checked exceptions.
- Generic return type.

Example:

```java
Callable<String> task = () -> {
    return "Hello";
};
```

---

## Runnable vs Callable

| Aspect                         | `Runnable`                      | `Callable<T>`                  |
|--------------------------------|----------------------------------|--------------------------------|
| Method                         | `run()`                         | `call()`                       |
| Return type                    | `void`                          | `T`                            |
| Checked exceptions allowed     | No                              | Yes                            |
| Usage                          | `execute()` / `submit()`        | `submit()`                     |
| Result                         | No result                       | Produces a result              |

---

## `submit(Callable)`

Example:

```java
Future<String> future = executor.submit(callable);
```

Execution flow:

```text
Callable
↓
submit()
↓
ExecutorService
↓
Worker Thread
↓
call()
↓
Future
↓
get()
```

---

## `Future`

A **`Future`** represents the result of an asynchronous computation.

Example:

```java
Future<Integer> future = executor.submit(() -> 42);
```

The task executes in another thread, while the current thread continues executing.

---

## Future Lifecycle

```text
submit()
        │
        ▼
Future Created
        │
Task Running
        │
isDone() = false
        │
Task Completes
        │
Result Stored
        │
isDone() = true
        │
get()
        │
Return Result
```

---

## Important Future Methods

### `get()`

```java
T result = future.get();
```

Waits until the computation completes. If the task is still running, the calling thread blocks until the result is available or an exception is thrown.

---

### `get(long timeout, TimeUnit unit)`

```java
T result = future.get(1, TimeUnit.SECONDS);
```

Waits up to the specified timeout for the result. Throws `TimeoutException` if the task is not done in time.

---

### `isDone()`

```java
boolean done = future.isDone();
```

Returns `true` if the computation has finished; otherwise returns `false`. Useful when polling task completion without blocking.

---

### `cancel(boolean mayInterruptIfRunning)`

```java
boolean cancelled = future.cancel(true);
```

Attempts to cancel the task. The boolean parameter specifies whether the running thread should be interrupted. Cancellation succeeds only if the task has not already completed.

---

## Blocking Operations

Some methods pause the current thread until a condition is satisfied. These are called **blocking operations**.

Examples:

```java
future.get();
blockingQueue.take();
thread.join();
object.wait();
```

Blocking is not inherently bad; it simply means:

> "Pause until the requested operation can continue."

Modern Java applications often combine blocking operations with thread pools or virtual threads to achieve high scalability.

---

## Best Practices (Executor & Future)

- Prefer `ExecutorService` over manually creating threads.
- Reuse worker threads through thread pools.
- Use `Runnable` when no result is required.
- Use `Callable<T>` when a result or checked exception handling is needed.
- Avoid calling `Future.get()` immediately after `submit()` unless you truly need the result, as it blocks the current thread.
- Always shut down every `ExecutorService` when it is no longer needed:

```java
executor.shutdown();
```

or, in a `try`-with-resources style via frameworks that manage the lifecycle for you.

---

## Common Interview Questions (Executor & Future)

### Why is `ExecutorService` preferred over creating threads manually?

Because it reuses worker threads, reduces thread-creation overhead, improves scalability, and provides lifecycle management and monitoring.

---

### What is the difference between `execute()` and `submit()`?

- `execute()` accepts a `Runnable` and returns `void`.
- `submit()` accepts a `Runnable` or `Callable` and returns a `Future` representing the task's lifecycle and (optionally) result.

---

### Can a `Runnable` return a value?

No. Its `run()` method has a `void` return type. If you need a result, use `Callable<T>` with `submit()`.

---

### Why would you use a `Callable`?

When the task needs to:

- Return a result.
- Throw checked exceptions.
- Or both.

---

### Is `Future.get()` asynchronous?

No. The computation is asynchronous, but `Future.get()` is a **blocking** method. It waits until the task has completed before returning its result.
