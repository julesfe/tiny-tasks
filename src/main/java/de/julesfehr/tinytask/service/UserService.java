package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;

public interface UserService {

  User findByEmail(String email);

  User findByToken(String token);

  void saveUser(User user);

  void enableUser(String token);

  boolean isTokenPresent(String token);

}
