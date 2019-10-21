package de.julesfehr.tinytask.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {

  @NonNull
  private final JavaMailSender mailSender;

  @Async
  public void sendMail(SimpleMailMessage email) {
    mailSender.send(email);
  }

}
