package de.julesfehr.tinytask.service;

import static java.util.stream.Collectors.toList;

import de.julesfehr.tinytask.domain.Task;
import de.julesfehr.tinytask.dto.TaskRequest;
import de.julesfehr.tinytask.dto.TaskResponse;
import de.julesfehr.tinytask.exception.TaskNotFoundException;
import de.julesfehr.tinytask.repository.TaskRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultTaskService implements TaskService {

  @NonNull
  private final TaskRepository taskRepository;

  @NonNull
  private final UserService userService;

  @NonNull
  private final MapperFacade mapperFacade;

  @Override
  @Transactional
  public TaskResponse createTask(TaskRequest taskRequest) {
    log.debug("createTask(createTask={})", taskRequest);
    Task task = mapperFacade.map(taskRequest, Task.class);
    return transformToResponse(taskRepository.save(task));
  }

  @Override
  @Transactional(readOnly = true)
  public List<TaskResponse> getTasks() {
    log.debug("getTasks()");
    return taskRepository.findAll().stream().map(this::transformToResponse).collect(toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<TaskResponse> getTasksByEmail(String email) {
    log.debug("getTasks for user with email {}", email);
    Optional<List<Task>> tasks = taskRepository
      .findAllTasksByUser(userService.findByEmail(email));
    return tasks.orElse(new ArrayList<>())
      .stream().map(this::transformToResponse).collect(toList());
  }

  private TaskResponse transformToResponse(Task task) {
    return mapperFacade.map(task, TaskResponse.class);
  }

  @Override
  @Transactional
  public void deleteTask(String taskId) {
    log.debug("deleteTask(taskId={})", taskId);
    taskRepository.delete(getTaskOrThrowException(taskId));
  }

  private Task getTaskOrThrowException(String taskId) {
    return taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
  }

}
