package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;

public interface UserService {

  User findByEmail(String email);

  void saveUser(User user);

}
