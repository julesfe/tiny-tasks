package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.dto.UserRequest;

public interface UserService {

  User findByEmail(String email);

  User findByToken(String token);

  User saveUser(UserRequest userRequest);

  void enableUser(String token);

  boolean isTokenPresent(String token);

}
