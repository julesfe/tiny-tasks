package de.julesfehr.tinytask.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.dto.UserRequest;
import java.util.UUID;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest extends BaseControllerTest {

  private static final String PATH_LOGIN = "/login";
  private static final String PATH_REGISTRATION = "/register";
  private static final String PATH_CONFIRMATION = "/confirm";

  @Test
  public void should_return_registration_form_template() throws Exception {
    ResultActions actualResult = this.mockMvc.perform(get(PATH_REGISTRATION));

    actualResult.andExpect(ResultMatcher.matchAll(
      model().attributeExists("user"),
      view().name("register")));
  }

  @Test
  public void should_return_confirmation_page_template() throws Exception {
    ResultActions actualResult = this.mockMvc.perform(get(PATH_CONFIRMATION).param("token", ""));

    actualResult.andExpect(ResultMatcher.matchAll(
      view().name("confirm")));
  }

  @Test
  public void should_add_user_exists_message_to_model_when_user_exists() throws Exception {
    String password = "hunter2";
    String email = "test@testmail.de";
    User user = new User(1, email, password, null, "", true);
    given(userService.findByEmail(anyString())).willReturn(user);

    ResultActions actualResult = this.mockMvc.perform(post(PATH_REGISTRATION)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .content("email=" + email + "&password=x&ConfirmPassword=x"));

    actualResult.andExpect(ResultMatcher.matchAll(
      model().attributeExists("userExistsMessage")));
  }

  @Test
  public void should_save_user_when_user_did_not_exist_before() throws Exception {
    String email = "test@testmail.de";
    String password = "password";
    UUID uuid = UUID.randomUUID();
    User user = new User(0, email, password, null, uuid.toString(), false);
    UserRequest userRequest = UserRequest.builder().email(email).password(password).build();
    given(userService.findByEmail(email)).willReturn(null);
    given(userService.saveUser(userRequest)).willReturn(user);
    given(uuidGenerator.generateId()).willReturn(uuid);

    this.mockMvc.perform(post(PATH_REGISTRATION)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .content("email=" + email + "&password=" + password));

    verify(userService, times(1)).saveUser(userRequest);
  }

  @Test
  public void should_send_confirmation_email_when_user_did_not_exist_before() throws Exception {
    String email = "test@testmail.de";
    String password = "password";
    UUID uuid = UUID.randomUUID();
    User user = new User(0, email, password, null, uuid.toString(), false);
    UserRequest userRequest = UserRequest.builder().email(email).password(password).build();
    given(userService.findByEmail(any())).willReturn(null);
    given(userService.saveUser(userRequest)).willReturn(user);
    given(uuidGenerator.generateId()).willReturn(uuid);

    this.mockMvc.perform(post(PATH_REGISTRATION)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .content("email=" + email + "&password=" + password));

    verify(emailService, times(1)).sendConfirmationMail(user, "http://localhost:80");
  }

  @Test
  public void should_add_confirmation_message_to_model_when_user_has_been_saved() throws Exception {
    String email = "test@testmail.de";
    String password = "password";
    UUID uuid = UUID.randomUUID();
    User user = new User(0, email, password, null, uuid.toString(), false);
    UserRequest userRequest = UserRequest.builder().email(email).password(password).build();
    given(userService.findByEmail(any())).willReturn(null);
    given(userService.saveUser(userRequest)).willReturn(user);
    given(uuidGenerator.generateId()).willReturn(UUID.randomUUID());

    ResultActions actualResult = this.mockMvc.perform(post(PATH_REGISTRATION)
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .content("email=" + email + "&password=" + password));

    actualResult.andExpect(ResultMatcher.matchAll(
      model().attributeExists("confirmationMessage")));
  }

  @Test
  public void should_enable_user_when_user_has_been_saved() throws Exception {
    String token = "token";
    given(userService.isTokenPresent(token)).willReturn(true);

    this.mockMvc.perform(get(PATH_CONFIRMATION).param("token", token));

    verify(userService, times(1)).enableUser(token);
  }

  @Test
  public void should_return_status_code_200_when_login_was_successful() throws Exception {
    String password = "hunter2";
    String email = "test@testmail.de";
    User user = new User(1, email, password, null, "", true);
    given(userService.findByEmail(anyString())).willReturn(user);

    ResultActions actualResult = this.mockMvc.perform(post(PATH_LOGIN)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content("{\"email\":\"test\",\"password\":\"hunter2\"}"));

    actualResult.andExpect(status().isOk());
  }

  @Test
  public void should_return_status_code_200_when_registration_was_successful() throws Exception {
    ResultActions actualResult = this.mockMvc.perform(post(PATH_REGISTRATION)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content("{\"email\":\"test@mail.de\",\"password\":\"hunter2\"}"));

    actualResult.andExpect(status().isOk());
  }

}
