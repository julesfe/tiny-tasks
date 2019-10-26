package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.dto.UserRequest;
import de.julesfehr.tinytask.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DefaultUserService implements UserService {

  @NonNull
  private final UserRepository userRepository;

  @NonNull
  private final MapperFacade mapperFacade;

  @Override
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  @Override
  public User findByToken(String token) {
    return userRepository.findByConfirmationToken(token).orElse(null);
  }

  @Override
  public User saveUser(UserRequest userRequest) {
    log.debug("saveUser for user {}", userRequest.getEmail());
    User user = mapperFacade.map(userRequest, User.class);
    return userRepository.save(user);
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
