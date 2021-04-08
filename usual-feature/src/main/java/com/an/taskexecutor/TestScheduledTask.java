package com.an.taskexecutor;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class TestScheduledTask {

  private int fixedRateCounter = 0;
  private int fixedDelayCounter = 0;
  private int initialDelayCounter = 0;
  private int cronCounter = 0;

  @Scheduled(fixedRate = 6000)
  public void scheduledAtFixedRate() {
    System.out.println("<R> Increment at fixed rate");
    fixedRateCounter++;
  }

  @Scheduled(fixedDelay = 6000)
  public void scheduledAtFixedDelay() {
    System.out.println("<D> Incrementing at fixed delay");
    fixedDelayCounter++;
  }

  @Scheduled(fixedDelay = 6000, initialDelay = 6000)
  public void scheduledWithInitialDelay() {
    System.out.println("<DI> Incrementing with initial delay");
    initialDelayCounter++;
  }

  @Scheduled(cron = "**/6 ** ** ** ** **")
  public void scheduledWithCron() {
    System.out.println("<C> Incrementing with cron");
    cronCounter++;

  }

  public int getFixedRateCounter() {
    return this.fixedRateCounter;
  }

  public int getFixedDelayCounter() {
    return this.fixedDelayCounter;
  }

  public int getInitialDelayCounter() {
    return this.initialDelayCounter;
  }

  public int getCronCounter() {
    return this.cronCounter;
  }

}