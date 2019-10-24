package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.repository.UserRepository;
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
  public void saveUser(User user) {
    userRepository.save(user);
  }

  @Override
  public boolean checkForDuplicate(String token) {
    return userRepository.findByConfirmationToken(token).isPresent();
  }

}
