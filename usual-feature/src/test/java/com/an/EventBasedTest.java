package com.an;

import com.an.event.Mouse;
import org.junit.Test;

import static org.springframework.test.util.AssertionErrors.assertTrue;

public class EventBasedTest {

  @Test
  public void test() {
    Mouse mouse = new Mouse();
    mouse.addListener(mouse12 -> {
      System.out.println("Listener#1 called");
      mouse12.addListenerCallback();
    });
    mouse.addListener(mouse1 -> {
      System.out.println("Listener#2 called");
      mouse1.addListenerCallback();
    });
    mouse.click();
    assertTrue("2 listeners should be invoked but only "+mouse.getListenerCallbacks()+" were", mouse.getListenerCallbacks() == 2);
  }
}