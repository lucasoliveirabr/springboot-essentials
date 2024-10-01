package academy.devdojo.springboot.repository;

import academy.devdojo.springboot.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  AppUser findByUsername(String username);
}