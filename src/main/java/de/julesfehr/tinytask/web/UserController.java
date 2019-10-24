package de.julesfehr.tinytask.web;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.service.EmailService;
import de.julesfehr.tinytask.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  public static final String REGISTER = "register";

  @NonNull
  private final PasswordEncoder passwordEncoder;

  @NonNull
  private final UserService userService;

  @NonNull
  private final EmailService emailService;

  @GetMapping("/register")
  public ModelAndView showRegistrationForm(ModelAndView modelAndView, User user) {
    log.debug("showing registration form for");
    modelAndView.addObject("user", user);
    modelAndView.setViewName(REGISTER);
    return modelAndView;
  }

  @PostMapping("/register")
  public ModelAndView submitRegistrationForm(ModelAndView modelAndView, @Valid User user,
    BindingResult bindingResult, HttpServletRequest request) {
    log.debug("processing registration form for user {}", user.getEmail());

    User userEntity = userService.findByEmail(user.getEmail());
    if (userEntity != null) {
      log.debug("user {} already exists", userEntity.getEmail());
      addUserExistsMessageToModel(modelAndView);
      bindingResult.reject("userExists");
    }
    if (bindingResult.hasErrors()) {
      modelAndView.setViewName(REGISTER);
    } else {
      userService.saveUser(user);
      sendConfirmationMail(user, request);
      addConfirmationMessageToModel(modelAndView);
      log.debug("user {} completed the registration process", user.getEmail());
    }
    return modelAndView;
  }

  @PostMapping("/login")
  public boolean login(@RequestBody User user) {
    log.debug("login User {}", user);
    return userService.findByEmail(user.getEmail()).getPassword().equals(user.getPassword());
  }

  @GetMapping
    ("/confirm")
  public void confirm(@RequestBody User user) {
    log.debug("confirming User {}", user);
  }

  private void addUserExistsMessageToModel(ModelAndView modelAndView) {
    modelAndView.addObject("userExistsMessage", "A user with that email already exists.");
    modelAndView.setViewName(REGISTER);
  }

  private void sendConfirmationMail(@Valid User user, HttpServletRequest request) {
    String url = request.getScheme() + "//" + request.getServerName();
    emailService.sendConfirmationMail(user, url);
  }

  private void addConfirmationMessageToModel(ModelAndView modelAndView) {
    modelAndView.addObject("confirmationMessage",
      "Registration successful! Please check your emails to confirm your registration.");
    modelAndView.setViewName(REGISTER);
  }
}
