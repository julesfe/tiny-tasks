package de.julesfehr.tinytask.repository;

import de.julesfehr.tinytask.domain.Task;
import de.julesfehr.tinytask.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, String> {

  Optional<List<Task>> findAllTasksByUser(User user);

}
