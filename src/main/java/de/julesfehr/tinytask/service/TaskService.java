package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.dto.TaskRequest;
import de.julesfehr.tinytask.dto.TaskResponse;
import java.util.List;

public interface TaskService {

  TaskResponse createTask(TaskRequest taskRequest);

  List<TaskResponse> getTasks();

  List<TaskResponse> getTasksByEmail(String email);

  void deleteTask(String taskId);

}
