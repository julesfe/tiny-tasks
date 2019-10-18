package de.julesfehr.tinytask.web;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  @Autowired
  private final PasswordEncoder passwordEncoder;
  private final UserService userService;

  @GetMapping("/register")
  public ModelAndView showRegistrationForm(ModelAndView modelAndView, User user) {
    log.debug("registering User {}", user);
    modelAndView.addObject("user", user);
    modelAndView.setViewName("register");
    return modelAndView;
  }

  @PostMapping("/login")
  public boolean login(@RequestBody User user) {
    log.debug("login User {}", user);
    return userService.findByUsername(user.getUsername()).getPassword().equals(user.getPassword());
  }

  @GetMapping
    ("/confirm")
  public void confirm(@RequestBody User user) {
    log.debug("confirming User {}", user);
  }
}
