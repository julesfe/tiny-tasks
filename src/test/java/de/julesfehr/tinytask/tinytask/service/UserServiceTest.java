package de.julesfehr.tinytask.tinytask.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import de.julesfehr.tinytask.domain.Task;
import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.repository.UserRepository;
import de.julesfehr.tinytask.service.UserService;
import java.util.Arrays;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @InjectMocks
  private UserService userDetailsService;

  @Mock
  private UserRepository userRepository;

  @Test
  public void should_return_user_for_given_user() {
    Task task = mock(Task.class);
    given(userRepository.findByUsername(anyString()))
      .willReturn(
        Optional.of(new User("123", "test@testmail.de", "test", "hunter2", Arrays.asList(task))));

    User result = userDetailsService.findByUsername("test");

    assertThat(result)
      .isEqualTo(new User("123", "test@testmail.de", "test", "hunter2", Arrays.asList(task)));
  }

  @Test(expected = EntityNotFoundException.class)
  public void should_throw_exception_when_user_is_not_found() {
    given(userRepository.findByUsername(anyString()))
      .willReturn(Optional.empty());

    User result = userDetailsService.findByUsername("testUser");
  }
}
