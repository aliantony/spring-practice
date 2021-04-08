package com.an;

import com.an.taskexecutor.AsyncCounter;
import com.an.taskexecutor.TestScheduledTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ScheduleAnnotationTest {

  @Autowired
  private GenericApplicationContext context;

  @Test
  public void testScheduled() throws InterruptedException {

      System.out.println("Start sleeping");
      Thread.sleep(6000);
      System.out.println("Wake up !");

      TestScheduledTask scheduledTask = (TestScheduledTask) context.getBean("testScheduledTask");
       /**
        * Test fixed delay. It's executed every 6 seconds. The first execution is registered after application's context start.
        **/
      assertTrue("Scheduled task should be executed 2 times (1 before sleep in this method, 1 after the sleep), but was "+scheduledTask.getFixedDelayCounter(),
        scheduledTask.getFixedDelayCounter() == 2);

       /**
        * Test fixed rate. It's executed every 6 seconds. The first execution is registered after application's context start.
        * Unlike fixed delay, a fixed rate configuration executes one task with specified time. For example, it will execute on
        * 6 seconds delayed task at 10:30:30, 10:30:36, 10:30:42 and so on - even if the task 10:30:30 taken 30 seconds to
        * be terminated. In teh case of fixed delay, if the first task takes 30 seconds, the next will be executed 6 seconds
        * after the first one, so the execution flow will be: 10:30:30, 10:31:06, 10:31:12.
        **/
      assertTrue("Scheduled task should be executed 2 times (1 before sleep in this method, 1 after the sleep), but was "+scheduledTask.getFixedRateCounter(),
        scheduledTask.getFixedRateCounter() == 2);
       /**
        * Test fixed rate with initial delay attribute. The initialDelay attribute is set to 6 seconds. It causes that
        * scheduled method is executed 6 seconds after application's context start. In our case, it should be executed
        * only once because of previous Thread.sleep(6000) invocation.
        **/
      assertTrue("Scheduled task should be executed 1 time (0 before sleep in this method, 1 after the sleep), but was "+scheduledTask.getInitialDelayCounter(), scheduledTask.getInitialDelayCounter() == 1);
       /**
        * Test cron scheduled task. Cron is scheduled to be executed every 6 seconds. It's executed only once,
        * so we can deduce that it's not invoked directly before applications
        * context start, but only after configured time (6 seconds in our case).
        **/
      assertTrue("Scheduled task should be executed 1 time (0 before sleep in this method, 1 after the sleep), but was "+scheduledTask.getCronCounter(), scheduledTask.getCronCounter() == 1);
  }

  @Test
  public void testAsyc() throws InterruptedException {
       /**
        * To test @Async annotation, we can create a bean in-the-fly. AsyncCounter bean is a
        * simple counter which value should be equals to 2 at the end of the test. A supplementary test
        * concerns threads which execute both of AsyncCounter methods: one which
        * isn't annotated with @Async and another one which is annotated with it. For the first one, invoking
        * thread should have the same name as the main thread. For annotated method, it can't be executed in
        * the main thread. It must be executed asynchrounously in a new thread.
        **/
      context.registerBeanDefinition("asyncCounter", new RootBeanDefinition(AsyncCounter.class));

      String currentName = Thread.currentThread().getName();
      AsyncCounter asyncCounter = context.getBean("asyncCounter", AsyncCounter.class);
      asyncCounter.incrementNormal();
      assertTrue("Thread executing normal increment should be the same as JUnit thread but it wasn't (expected '"+currentName+"', got '"+asyncCounter.getNormalThreadName()+"')",
                      asyncCounter.getNormalThreadName().equals(currentName));
      asyncCounter.incrementAsync();
      // sleep 50ms and give some time to AsyncCounter to update asyncThreadName value
      Thread.sleep(50);

      assertFalse("Thread executing @Async increment shouldn't be the same as JUnit thread but it wasn (JUnit thread '"+currentName+"', @Async thread '"+asyncCounter.getAsyncThreadName()+"')",
                      asyncCounter.getAsyncThreadName().equals(currentName));
      System.out.println("Main thread execution's name: "+currentName);
      System.out.println("AsyncCounter normal increment thread execution's name: "+asyncCounter.getNormalThreadName());
      System.out.println("AsyncCounter @Async increment thread execution's name: "+asyncCounter.getAsyncThreadName());
      assertTrue("Counter should be 2, but was "+asyncCounter.getCounter(), asyncCounter.getCounter()==2);
  }

}