package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultEmailService implements EmailService {

  @NonNull
  private final JavaMailSender mailSender;

  private static final String CONFIRMATION_MAIL_FROM = "TinyTasksApplication@gmail.com";
  private static final String CONFIRMATION_MAIL_SUBJECT = "Tiny Tasks E-Mail Confirmation";
  private static final String CONFIRMATION_MAIL_TEXT = "To confirm your e-mail address, please click the link below:\n";

  @Async
  @Override
  public void sendConfirmationMail(User user, String url) {
    log.debug("sendConfirmationMail() for user {}", user.getEmail());
    SimpleMailMessage message = new SimpleMailMessage();
    setMessageData(user, url, message);
    mailSender.send(message);
  }

  private void setMessageData(User user, String url, SimpleMailMessage message) {
    message.setTo(user.getEmail());
    message.setFrom(CONFIRMATION_MAIL_FROM);
    message.setSubject(CONFIRMATION_MAIL_SUBJECT);
    message.setText(CONFIRMATION_MAIL_TEXT + url + "/confirm?token=");
//      + user.getConfirmationToken());
  }
}
