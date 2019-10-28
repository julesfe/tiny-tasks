package de.julesfehr.tinytask.web;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.dto.UserRequest;
import de.julesfehr.tinytask.helper.UUIDGeneratorHelper;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private static final String REGISTER = "register";

  @NonNull
  private final UserService userService;

  @NonNull
  private final EmailService emailService;

  @NonNull
  private final PasswordEncoder passwordEncoder;

  @NonNull
  private final UUIDGeneratorHelper uuidGenerator;

  @GetMapping("/register")
  public ModelAndView showRegistrationForm(ModelAndView modelAndView, UserRequest userRequest) {
    log.debug("showing registration form for");
    modelAndView.addObject("user", userRequest);
    modelAndView.setViewName(REGISTER);

    return modelAndView;
  }

  @PostMapping("/register")
  public ModelAndView submitRegistrationForm(ModelAndView modelAndView,
    @Valid @NonNull @ModelAttribute("user") UserRequest userRequest,
    BindingResult bindingResult,
    HttpServletRequest request) {
    log.debug("processing registration form for user {}", userRequest.getEmail());

    User user = userService.findByEmail(userRequest.getEmail());
    if (user != null) {
      log.debug("userRequest {} already exists", user.getEmail());
      addUserExistsMessageToModel(modelAndView);
      bindingResult.reject("userExists");
    }

    if (bindingResult.hasErrors()) {
      modelAndView.setViewName(REGISTER);
    } else {
      user = userService.saveUser(userRequest);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      sendConfirmationMail(user, request);
      addConfirmationMessageToModel(modelAndView);
      log.debug("user {} completed the registration process", userRequest.getEmail());
    }

    return modelAndView;
  }

  @GetMapping("/confirm")
  public ModelAndView showConfirmationPage(ModelAndView modelAndView,
    @RequestParam("token") String token) {

    if (!userService.isTokenPresent(token)) {
      modelAndView.addObject("invalidToken",
        "Oops! It looks like your confirmation link was invalid.");
    } else {
      modelAndView.addObject("confirmationToken", token);
      userService.enableUser(token);
    }

    modelAndView.setViewName("confirm");
    return modelAndView;
  }

  @PostMapping("/login")
  public boolean login(@RequestBody UserRequest user) {
    log.debug("login User {}", user);
    return userService.findByEmail(user.getEmail()).getPassword().equals(user.getPassword());
  }

  private void addUserExistsMessageToModel(ModelAndView modelAndView) {
    modelAndView.addObject("userExistsMessage", "A user with that email already exists.");
    modelAndView.setViewName(REGISTER);
  }

  private void sendConfirmationMail(@Valid User user, HttpServletRequest request) {
    setConfirmationToken(user);
    String url =
      request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    emailService.sendConfirmationMail(user, url);
  }

  private void setConfirmationToken(@Valid User user) {
    String token = uuidGenerator.generateId().toString();
    if (userService.isTokenPresent(token)) {
      setConfirmationToken(user);
    } else {
      user.setConfirmationToken(token);
    }
  }

  private void addConfirmationMessageToModel(ModelAndView modelAndView) {
    modelAndView.addObject("confirmationMessage",
      "Registration successful! Please check your emails to confirm your registration.");
    modelAndView.setViewName(REGISTER);
  }
}
