package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User findByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(
      () -> new EntityNotFoundException("could not find user with username " + username));
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(
      () -> new EntityNotFoundException("could not find user with email " + email));
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }
}
