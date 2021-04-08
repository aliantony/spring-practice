package com.an.taskexecutor;

public enum Counters {

  simpleAsyncTask(0),
  syncTask(0),
  threadPool(0);

  private int nb;

  public int getNb() {
    return this.nb;
  }

  public synchronized void increment() {
    this.nb++;
  }

  private Counters(int n) {
    this.nb = n;
  }
}