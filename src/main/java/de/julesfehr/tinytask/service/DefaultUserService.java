package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultUserService implements UserService {

  @NonNull
  private final UserRepository userRepository;

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  @Override
  public User findByToken(String token) {
    return userRepository.findByConfirmationToken(token).orElse(null);
  }

  @Override
  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Override
  public void enableUser(String token) {
    userRepository.findByConfirmationToken(token)
      .orElseThrow((() -> new EntityNotFoundException()))
      .setEnabled(true);
  }

  @Override
  public boolean isTokenPresent(String token) {
    return userRepository.findByConfirmationToken(token).isPresent();
  }

}
