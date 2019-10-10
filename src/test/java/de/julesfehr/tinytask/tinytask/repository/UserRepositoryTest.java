package de.julesfehr.tinytask.tinytask.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import de.julesfehr.tinytask.domain.Task;
import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.repository.UserRepository;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = NONE)
@DataJpaTest
public class UserRepositoryTest {

  @Autowired
  TestEntityManager testEntityManager;

  @Autowired
  UserRepository userRepository;

  @Test
  public void should_return_user_entity_for_given_user() {
    Task task = givenTask(new Task(null, "taskName", Instant.now()));
    User user = new User("123", "test@testmail.de", "testUser", "hunter2", null);
    user = givenUser(user);
    task = givenTask(task);
    testEntityManager.find(Task.class, task.getId())
      .setUser(testEntityManager.find(User.class, user.getId()));
    testEntityManager.find(User.class, user.getId())
      .setTasks(Arrays.asList(testEntityManager.find(Task.class, task.getId())));

    Optional<User> result = userRepository.findByUsername(user.getUsername());

    assertThat(result).isEqualTo(Optional.of(user));
  }

  private User givenUser(User user) {
    return testEntityManager.merge(user);
  }

  private Task givenTask(Task task) {
    return testEntityManager.merge(task);
  }
}
