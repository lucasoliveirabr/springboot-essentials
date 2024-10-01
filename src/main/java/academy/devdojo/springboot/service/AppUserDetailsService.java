package academy.devdojo.springboot.service;

import academy.devdojo.springboot.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
  private final AppUserRepository appUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    return Optional.ofNullable(appUserRepository.findByUsername(username))
        .orElseThrow(() -> new UsernameNotFoundException("App user not found."));
  }
}