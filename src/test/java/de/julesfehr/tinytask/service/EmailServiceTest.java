package de.julesfehr.tinytask.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EmailServiceTest {

  @InjectMocks
  EmailService emailService;

  @Mock
  JavaMailSender mailSender;

  @Test
  public void should_send_email() {
    SimpleMailMessage email = new SimpleMailMessage();
    email.setTo("Test@Testmail.com");
    email.setSubject("Testmail");
    email.setText("Testtext");
    email.setFrom("FromTinyTask@TestMail.com");

    emailService.sendMail(email);

    verify(mailSender, times(1)).send(email);
  }

  @Test
  public void should_send_email_with_correct_recipient_and_message() {
//    String recipient = "TinyTasksApplication@gmail.com";
//    String message = "Message";
//
//    emailService.sendMail();
//
//    String content = "<span>" + message + "<span>";
//    assertReceivedMessageContains("");
  }

  private void assertReceivedMessageContains(String message) {
  }

}
