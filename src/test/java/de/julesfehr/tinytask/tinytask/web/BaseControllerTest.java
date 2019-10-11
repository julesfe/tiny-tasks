package de.julesfehr.tinytask.tinytask.web;

import de.julesfehr.tinytask.repository.UserRepository;
import de.julesfehr.tinytask.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.julesfehr.tinytask.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
abstract public class BaseControllerTest {

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected MockMvc mockMvc;

  @MockBean
  protected TaskService taskService;

  @MockBean
  protected UserService userService;

  @MockBean
  protected BCryptPasswordEncoder bCryptPasswordEncoder;

}
