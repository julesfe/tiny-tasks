package de.julesfehr.tinytask.web;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  @PostMapping("/login")
  public boolean login(@RequestBody User user) {
    log.debug("login User {}", user);
    User userEntity = userRepository.findByUsername(user.getUsername()).orElseThrow(
      () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
        "could not login user with username " + user.getUsername()));
    return user.getPassword().equals(userEntity.getPassword());
  }

  @PostMapping("/register")
  public void registration(@RequestBody User user) {
    log.debug("registering User {}", user);
  }

  @GetMapping("/confirm")
  public void confirm(@RequestBody User user) {
    log.debug("confirming User {}", user);
  }
}
