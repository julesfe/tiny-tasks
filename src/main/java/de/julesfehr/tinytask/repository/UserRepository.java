package de.julesfehr.tinytask.repository;

import de.julesfehr.tinytask.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findById(String id);

  Optional<User> findByUsername(String username);

}