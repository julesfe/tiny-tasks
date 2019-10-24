package de.julesfehr.tinytask.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.julesfehr.tinytask.domain.Task;
import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.dto.TaskRequest;
import de.julesfehr.tinytask.dto.TaskResponse;
import de.julesfehr.tinytask.exception.TaskNotFoundException;
import de.julesfehr.tinytask.repository.TaskRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class DefaultTaskServiceTest {

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private DefaultUserService userService;

  @Mock
  private MapperFacade mapperFacade;

  @InjectMocks
  private DefaultTaskService taskService;

  @Test
  public void should_create_task() {
    // given
    TaskRequest taskRequest = mock(TaskRequest.class);
    Task task = mock(Task.class);
    Task savedTask = mock(Task.class);
    TaskResponse taskResponse = mock(TaskResponse.class);
    doReturn(task).when(mapperFacade).map(taskRequest, Task.class);
    given(taskRepository.save(task)).willReturn(savedTask);
    doReturn(taskResponse).when(mapperFacade).map(savedTask, TaskResponse.class);

    // when
    TaskResponse actualResponse = taskService.createTask(taskRequest);

    // then
    assertThat(actualResponse).isEqualTo(taskResponse);
  }

  @Test
  public void should_get_tasks() {
    // given
    Task task = mock(Task.class);
    TaskResponse taskResponse = mock(TaskResponse.class);
    given(taskRepository.findAll()).willReturn(Arrays.asList(task));
    when(mapperFacade.map(task, TaskResponse.class)).thenReturn(taskResponse);

    // when
    List<TaskResponse> actualTasks = taskService.getTasks();

    // then
    assertThat(actualTasks).contains(taskResponse);
  }


  @Test
  public void should_get_tasks_by_email() {
    // given
    Task task = mock(Task.class);
    TaskResponse taskResponse = mock(TaskResponse.class);
    List<Task> tasks = Arrays.asList(task);
    User user = new User(123, "test@testmail.de", "hunter2", tasks, "", true);
    when(taskRepository.findAllTasksByUser(user)).thenReturn(Optional.of(tasks));
    given(userService.findByEmail("test@testmail.de")).willReturn(user);
    when(mapperFacade.map(task, TaskResponse.class)).thenReturn(taskResponse);

    // when
    List<TaskResponse> actualTasks = taskService.getTasksByEmail("test@testmail.de");

    // then
    assertThat(actualTasks).contains(taskResponse);
  }

  @Test
  public void should_delete_task() {
    // given
    String id = "task-id";
    Task task = mock(Task.class);
    when(taskRepository.findById(id)).thenReturn(Optional.of(task));

    // when
    taskService.deleteTask(id);

    // then
    verify(taskRepository).delete(task);
  }

  @Test(expected = TaskNotFoundException.class)
  public void should_not_delete_task() {
    // given
    String id = "task-id";

    // when
    taskService.deleteTask(id);

    // then
    // -- see exception of test annotation
  }
}
