package de.julesfehr.tinytask.web;

import de.julesfehr.tinytask.dto.TaskRequest;
import de.julesfehr.tinytask.dto.TaskResponse;
import de.julesfehr.tinytask.service.TaskService;
import de.julesfehr.tinytask.service.UserService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  private final UserService userService;

  @PostMapping
  public TaskResponse createTask(@RequestBody @Valid TaskRequest taskRequest) {
    log.debug("createTask(createTask={})", taskRequest);
    taskRequest.setUser(userService.findByUsername(taskRequest.getUser().getUsername()));
    return taskService.createTask(taskRequest);
  }

  @GetMapping
  public List<TaskResponse> getTasks() {
    log.debug("getTasks()");
    return taskService.getTasks();
  }

  @GetMapping(path = "/{username}")
  public List<TaskResponse> getTasksByUsername(@PathVariable String username) {
    log.debug("getTasks() for user {}", username);
    return taskService.getTasksByUsername(username);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping(path = "/{taskId}")
  public void deleteTask(@PathVariable String taskId) {
    log.debug("deleteTask(taskId={})", taskId);
    taskService.deleteTask(taskId);
  }
}