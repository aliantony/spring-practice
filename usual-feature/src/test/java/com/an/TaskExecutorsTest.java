package com.an;

import com.an.taskexecutor.Counters;
import com.an.taskexecutor.SimpleTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
@WebAppConfiguration
public class TaskExecutorsTest {

  @Test
  public void simpeAsync() throws InterruptedException {
    /**
      * SimpleAsyncTaskExecutor creates new Thread for every task and executes it asynchronously. The threads aren't reused as in
      * native Java's thread pools.
      *
      * The number of concurrently executed threads can be specified through concurrencyLimit bean property
      * (concurrencyLimit XML attribute). Here it's more simple to invoke setConcurrencyLimit method.
      * Here the tasks will be executed by 2 simultaneous threads. Without specifying this value,
      * the number of executed threads will be indefinite.
      *
      * You can observe that only 2 tasks are executed at a given time - even if 3 are submitted to execution (lines 40-42).
      **/
    SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("thread_name_prefix_____");
    executor.setConcurrencyLimit(2);
    executor.execute(new SimpleTask("SimpleAsyncTask-1", Counters.simpleAsyncTask, 1000));
    executor.execute(new SimpleTask("SimpleAsyncTask-2", Counters.simpleAsyncTask, 1000));

    Thread.sleep(1050);
    assertTrue("2 threads should be terminated, but "+Counters.simpleAsyncTask.getNb()+" were instead", Counters.simpleAsyncTask.getNb() == 2);

    executor.execute(new SimpleTask("SimpleAsyncTask-3", Counters.simpleAsyncTask, 1000));
    executor.execute(new SimpleTask("SimpleAsyncTask-4", Counters.simpleAsyncTask, 1000));
    executor.execute(new SimpleTask("SimpleAsyncTask-5", Counters.simpleAsyncTask, 2000));

    Thread.sleep(1050);
    assertTrue("4 threads should be terminated, but "+Counters.simpleAsyncTask.getNb()+" were instead", Counters.simpleAsyncTask.getNb() == 4);
    executor.execute(new SimpleTask("SimpleAsyncTask-6", Counters.simpleAsyncTask, 1000));

    Thread.sleep(1050);
    assertTrue("6 threads should be terminated, but "+Counters.simpleAsyncTask.getNb()+" were instead",
      Counters.simpleAsyncTask.getNb() == 6);
  }

  @Test
  public void syncTaskTest() {
    /**
      * SyncTask works almost as Java's CountDownLatch. In fact, this executor is synchronous with the calling thread. In our case,
      * SyncTaskExecutor tasks will be synchronous with JUnit thread. It means that the testing thread will sleep 5
      * seconds after executing the third task ('SyncTask-3'). To prove that, we check if the total execution time is ~5 seconds.
      **/
    long start = System.currentTimeMillis();
    SyncTaskExecutor executor = new SyncTaskExecutor();
    executor.execute(new SimpleTask("SyncTask-1", Counters.syncTask, 0));
    executor.execute(new SimpleTask("SyncTask-2", Counters.syncTask, 0));
    executor.execute(new SimpleTask("SyncTask-3", Counters.syncTask, 0));
    executor.execute(new SimpleTask("SyncTask-4", Counters.syncTask, 5000));
    executor.execute(new SimpleTask("SyncTask-5", Counters.syncTask, 0));
    long end = System.currentTimeMillis();
    int execTime = Math.round((end-start)/1000);
    assertTrue("Execution time should be 5 seconds but was "+execTime+" seconds", execTime == 5);
  }

  @Test
  public void threadPoolTest() throws InterruptedException {
    /**
      * This executor can be used to expose Java's native ThreadPoolExecutor as Spring bean, with the
      * possibility to set core pool size, max pool size and queue capacity through bean properties.
      *
      * It works exactly as ThreadPoolExecutor from java.util.concurrent package. It means that our pool starts
      * with 2 threads (core pool size) and can be growth until 3 (max pool size).
      * In additionally, 1 task can be stored in the queue. This task will be treated
      * as soon as one from 3 threads ends to execute provided task. In our case, we try to execute 5 tasks
      * in 3 places pool and 1 place queue. So the 5th task should be rejected and TaskRejectedException should be thrown.
      **/
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(2);
    executor.setMaxPoolSize(3);
    executor.setQueueCapacity(1);
    executor.initialize();

    executor.execute(new SimpleTask("ThreadPoolTask-1", Counters.threadPool, 1000));
    executor.execute(new SimpleTask("ThreadPoolTask-2", Counters.threadPool, 1000));
    executor.execute(new SimpleTask("ThreadPoolTask-3", Counters.threadPool, 1000));
    executor.execute(new SimpleTask("ThreadPoolTask-4", Counters.threadPool, 1000));
    boolean wasTre = false;
    try {
      executor.execute(new SimpleTask("ThreadPoolTask-5", Counters.threadPool, 1000));
    } catch (TaskRejectedException tre) {
      wasTre = true;
    }
    assertTrue("The last task should throw a TaskRejectedException but it wasn't", wasTre);

    Thread.sleep(3000);

    assertTrue("4 tasks should be terminated, but "+Counters.threadPool.getNb()+" were instead",
      Counters.threadPool.getNb()==4);
  }

}