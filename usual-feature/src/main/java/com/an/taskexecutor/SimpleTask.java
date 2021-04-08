package com.an.taskexecutor;

public class SimpleTask implements Runnable {
  private String name;
  private Counters counter;
  private int sleepTime;

  public SimpleTask(String name, Counters counter, int sleepTime) {
    this.name = name;
    this.counter = counter;
    this.sleepTime = sleepTime;
  }

  @Override
  public void run() {
    try {
      Thread.sleep(this.sleepTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.counter.increment();
    System.out.println("Running task '"+this.name+"' in Thread "+Thread.currentThread().getName());
  }

  @Override
  public String toString() {
          return "Task {"+this.name+"}";
  }
}