package academy.devdojo.springboot.config;

import academy.devdojo.springboot.service.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final AppUserDetailsService appUserDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    /*
    CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();

    XorCsrfTokenRequestAttributeHandler delegate = new XorCsrfTokenRequestAttributeHandler();
    delegate.setCsrfRequestAttributeName("_csrf");
    CsrfTokenRequestHandler requestHandler = delegate::handle;
    */

    http
        .csrf(AbstractHttpConfigurer::disable)
        /*
        .csrf(csrf -> csrf
            .csrfTokenRepository(tokenRepository)
            .csrfTokenRequestHandler(requestHandler)
        )
        */
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/animes/admin/**").hasRole("ADMIN")
            .requestMatchers("/animes/**").hasRole("USER")
            .anyRequest().authenticated()
        )
        .formLogin(withDefaults())
        .httpBasic(withDefaults());
    return http.build();
  }

  /*
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    UserDetails userAdmin = User.withUsername("admin")
        .password(passwordEncoder.encode("academy"))
        .roles("USER", "ADMIN")
        .build();
    UserDetails userDevdojo = User.withUsername("devdojo")
        .password(passwordEncoder.encode("academy"))
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(userAdmin, userDevdojo);
  }
  */

  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity http, AppUserDetailsService appUserDetailsService
  ) throws Exception {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);

    authenticationManagerBuilder
        .userDetailsService(appUserDetailsService)
        .passwordEncoder(passwordEncoder);

    return authenticationManagerBuilder.build();
  }

}