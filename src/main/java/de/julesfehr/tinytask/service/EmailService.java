package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;

public interface EmailService {

  void sendConfirmationMail(User user, String url);

}
