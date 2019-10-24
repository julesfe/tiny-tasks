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
public class DefaultEmailService implements EmailService{

  @NonNull
  private final JavaMailSender mailSender;

  private static final String CONFIRMATION_MAIL_FROM = "TinyTasksApplication@gmail.com";
  private static final String CONFIRMATION_MAIL_SUBJECT = "Tiny Tasks E-Mail Confirmation";
  private static final String CONFIRMATION_MAIL_TEXT = "Tiny Tasks E-Mail Confirmation";

  @Async
  @Override
  public void sendConfirmationMail(User user) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setFrom(CONFIRMATION_MAIL_FROM);
    message.setSubject(CONFIRMATION_MAIL_SUBJECT);
    message.setText(CONFIRMATION_MAIL_TEXT);

    log.debug("sendConfirmationMail() for user {}", user.getEmail());

    mailSender.send(message);
  }
}
