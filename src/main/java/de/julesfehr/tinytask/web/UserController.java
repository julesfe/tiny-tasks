package de.julesfehr.tinytask.web;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserService userService;

  @PostMapping("/login")
  public boolean login(@RequestBody User user) {
    log.debug("login User {}", user);
    return userService.findByUsername(user.getUsername()).getPassword().equals(user.getPassword());
  }

  @PostMapping("/register")
  public void registration(@RequestBody User user) {
    log.debug("registering User {}", user);
  }

  @GetMapping
    ("/confirm")
  public void confirm(@RequestBody User user) {
    log.debug("confirming User {}", user);
  }
}
