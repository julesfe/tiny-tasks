package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.dto.TaskRequest;
import de.julesfehr.tinytask.dto.TaskResponse;
import java.util.List;

public interface TaskService {

  TaskResponse createTask(TaskRequest taskRequest);

  List<TaskResponse> getTasks();

  List<TaskResponse> getTasksByUsername(String username);

  void deleteTask(String taskId);

}
