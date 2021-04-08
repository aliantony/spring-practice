package com.an.eventasync;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * This is synchronous event dispatched when one product is modified in the backoffice.
 * When product's modification fails (database, validation problem), this event is dispatched to
 * all listeners. It's synchronous because we want to inform the user that some actions were done
 * after the failure. Otherwise (asynchronous character of event) we shouldn't be able to
 * know if something was done or not after the dispatch.
 **/
public class ProductChangeFailureEvent extends ApplicationContextEvent {

  private static final long serialVersionUID = -1681426286796814792L;
  public static final String TASK_KEY = "ProductChangeFailureEvent";

  public ProductChangeFailureEvent(ApplicationContext source) {
    super(source);
  }
}