package de.julesfehr.tinytask.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.julesfehr.tinytask.domain.Task;
import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.repository.UserRepository;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class DefaultUserServiceTest {

  @InjectMocks
  private DefaultUserService userService;

  @Mock
  private UserRepository userRepository;

  @Test
  public void should_return_user_when_searching_by_email() {
    Task task = mock(Task.class);
    String email = "test@testmail.de";
    User user = new User(123, email, "hunter2", Arrays.asList(task), "", true);
    given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

    User result = userService.findByEmail(email);

    assertThat(result).isEqualTo(user);
  }

  @Test
  public void should_save_user() {
    User user = new User(123, "test@testmail.de", "hunter2", null, "", false);

    userService.saveUser(user);

    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void should_enable_user() {
    String token = "token";
    User user = new User(123, "test@testmail.de", "hunter2", null, token, false);
    given(userRepository.findByConfirmationToken(token)).willReturn(Optional.of(user));

    userService.enableUser(token);

    assertThat(user.isEnabled()).isTrue();
  }

  @Test
  public void should_return_null_when_user_is_not_found() {
    given(userRepository.findByEmail(anyString())).willReturn(Optional.empty());

    User result = userService.findByEmail("testUser");

    assertThat(result).isEqualTo(null);
  }

  @Test
  public void should_return_false_when_token_is_not_in_use() {
    given(userRepository.findByConfirmationToken(anyString())).willReturn(Optional.empty());

    boolean result = userService.isTokenPresent("TestUUID1234");

    assertThat(result).isFalse();
  }

  @Test
  public void should_return_true_when_token_is_in_use() {
    User user = mock(User.class);
    given(userRepository.findByConfirmationToken(anyString())).willReturn(Optional.of(user));

    boolean result = userService.isTokenPresent("TestUUID1234");

    assertThat(result).isTrue();
  }

  @Test
  public void should_return_user_when_searching_by_token() {
    String token = "TestUUID1234";
    User user = new User(1, "", "", null, token, false);
    given(userRepository.findByConfirmationToken(token)).willReturn(Optional.of(user));

    User result = userService.findByToken(token);

    assertThat(result).isEqualTo(user);
  }

  @Test
  public void should_enable_user_when_confirmation_token_is_correct() {
    String token = "TestUUID1234";
    User user = new User(1, "", "", null, token, false);
    given(userRepository.findByConfirmationToken(token)).willReturn(Optional.of(user));

    userService.enableUser(token);

    assertThat(userService.findByToken(token).isEnabled()).isTrue();
  }
}
