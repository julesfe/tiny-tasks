package de.julesfehr.tinytask.tinytask.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.julesfehr.tinytask.domain.User;
import javax.persistence.EntityNotFoundException;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest extends BaseControllerTest {

  private static final String PATH_LOGIN = "/login";
  private static final String PATH_REGISTRATION = "/login";

  @Test
  public void should_return_status_code_200_when_login_was_successful() throws Exception {
    String username = "test";
    String password = "hunter2";
    String email = "test@testmail.de";
    User user = new User(null, email, username, password, null);
    given(userService.findByUsername(anyString())).willReturn(user);

    ResultActions actualResult = this.mockMvc.perform(post(PATH_LOGIN)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content("{\"username\":\"test\",\"password\":\"hunter2\"}"));

    actualResult.andExpect(status().isOk());
  }

  @Test
  public void should_return_status_code_401_when_login_was_not_successful() throws Exception {
    given(userService.findByUsername(anyString())).willThrow(new EntityNotFoundException());

    ResultActions actualResult = this.mockMvc.perform(post(PATH_LOGIN)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content("{\"username\":\"testUser\",\"password\":\"hunter2\"}"));

    actualResult.andExpect(status().is(401));
  }

  @Test
  public void should_return_status_code_200_when_registration_was_successful() throws Exception {
    String username = "test";
    String password = "hunter2";
    String email = "test@testmail.de";
    User user = new User(null, email, username, password, null);

    ResultActions actualResult = this.mockMvc.perform(post(PATH_REGISTRATION)
      .contentType(MediaType.APPLICATION_JSON_UTF8)
      .content("{\"username\":\"test\",\"password\":\"hunter2\"}"));

    actualResult.andExpect(status().isOk());
  }

}
