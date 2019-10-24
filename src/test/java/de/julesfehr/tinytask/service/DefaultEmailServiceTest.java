package de.julesfehr.tinytask.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import de.julesfehr.tinytask.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class DefaultEmailServiceTest {

  @Mock
  private JavaMailSender mailSender;

  @InjectMocks
  private DefaultEmailService emailService;

  @Test
  public void should_send_confirmation_mail_to_given_user() {
    User user = mock(User.class);
    given(user.getEmail()).willReturn("user@testmail.de");

    emailService.sendConfirmationMail(user);

    verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
  }

}
