package com.an.eventasync;

import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * Event dispatched asynchronously every time when we want to send a notification mail.
 * Notification mails to send should be stored somewhere (filesystem, database...) but in
 * our case, we'll handle only one notification mail: when one product out-of-stock becomes available again.
 **/
public class NotifMailDispatchEvent extends ApplicationContextEvent implements AsyncApplicationEvent {

  private static final long serialVersionUID = 9202282810553100778L;
  public static final String TASK_KEY = "NotifMailDispatchEvent";

  public NotifMailDispatchEvent(ApplicationContext source) {
    super(source);
  }
}