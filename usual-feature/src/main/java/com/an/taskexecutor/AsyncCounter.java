package com.an.taskexecutor;

import org.springframework.scheduling.annotation.Async;

public class AsyncCounter {

  private int counter = 0;
  private String normalThreadName;
  private String asyncThreadName;

  public void incrementNormal() {
    normalThreadName = Thread.currentThread().getName();
    this.counter++;
  }

  @Async
  public void incrementAsync() {
    asyncThreadName = Thread.currentThread().getName();
    this.counter++;
  }

  public String getAsyncThreadName() {
    return asyncThreadName;
  }

  public String getNormalThreadName() {
    return normalThreadName;
  }

  public int getCounter() {
    return this.counter;
  }

}