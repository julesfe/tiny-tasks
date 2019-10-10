package de.julesfehr.tinytask.service;

import de.julesfehr.tinytask.domain.User;
import de.julesfehr.tinytask.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User userEntity = userRepository.findByUsername(username)
      .orElseThrow(
        () -> new EntityNotFoundException("could not find user with username " + username));
    return new UserDetailsImpl(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword());
  }
}
