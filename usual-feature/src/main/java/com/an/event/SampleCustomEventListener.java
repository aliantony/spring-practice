package com.an.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SampleCustomEventListener implements ApplicationListener<SampleCustomEvent> {

  @Override
  public void onApplicationEvent(SampleCustomEvent event) {
    long start = System.currentTimeMillis();
    try {
      Thread.sleep(5000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    long end = System.currentTimeMillis();
    int testTime = Math.round((end - start) / 1000);
    ((TimeExecutorHolder) event.getApplicationContext().getBean("timeExecutorHolder")).addNewTime("sampleCustomEventListener", new Integer(testTime));
  }
}