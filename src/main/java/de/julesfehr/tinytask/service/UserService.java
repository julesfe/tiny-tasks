package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  public void saveUser(User user) {
    userRepository.save(user);
  }
}
