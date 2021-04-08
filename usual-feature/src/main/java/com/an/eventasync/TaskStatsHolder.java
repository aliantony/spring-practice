package com.an.eventasync;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/****
 ** Holder bean for all executed tasks.
 **/
@Component
public class TaskStatsHolder {

  private Map<String, TaskStatData> tasks = new HashMap<String, TaskStatData>();

  public void addNewTaskStatHolder(String key, TaskStatData value) {
    tasks.put(key, value);
  }

  public TaskStatData getTaskStatHolder(String key) {
    return tasks.get(key);
  }
}