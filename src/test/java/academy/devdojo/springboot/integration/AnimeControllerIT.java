package academy.devdojo.springboot.integration;

import academy.devdojo.springboot.domain.Anime;
import academy.devdojo.springboot.domain.AppUser;
import academy.devdojo.springboot.repository.AnimeRepository;
import academy.devdojo.springboot.repository.AppUserRepository;
import academy.devdojo.springboot.requests.AnimePostRequestBody;
import academy.devdojo.springboot.util.AnimeCreator;
import academy.devdojo.springboot.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnimeControllerIT {
  @Autowired
  @Qualifier(value = "testRestTemplateRoleUser")
  private TestRestTemplate testRestTemplateRoleUser;

  @Autowired
  @Qualifier(value = "testRestTemplateRoleAdmin")
  private TestRestTemplate testRestTemplateRoleAdmin;

  @Autowired
  private AnimeRepository animeRepository;

  @Autowired
  private AppUserRepository appUserRepository;

  private static final AppUser USER = AppUser.builder()
      .name("O DevDojo")
      .password("{bcrypt}$2a$10$WkS/abSlI1UTnpe1XAdPmOQDb1ReaNPVdKDB/yBOyNvMOBXu2h/JG")
      .username("devdojo")
      .authorities("ROLE_USER")
      .build();

  private static final AppUser ADMIN = AppUser.builder()
      .name("O Admin")
      .password("{bcrypt}$2a$10$WkS/abSlI1UTnpe1XAdPmOQDb1ReaNPVdKDB/yBOyNvMOBXu2h/JG")
      .username("admin")
      .authorities("ROLE_USER,ROLE_ADMIN")
      .build();

  @TestConfiguration
  @Lazy
  static class Config {
    @Bean(name = "testRestTemplateRoleUser")
    public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
      RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
          .rootUri("http://localhost:" + port)
          .basicAuthentication("devdojo", "academy");
      return new TestRestTemplate(restTemplateBuilder);
    }

    @Bean(name = "testRestTemplateRoleAdmin")
    public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
      RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
          .rootUri("http://localhost:" + port)
          .basicAuthentication("admin", "academy");
      return new TestRestTemplate(restTemplateBuilder);
    }
  }

  @Test
  @DisplayName("list returns list of animes inside page object when successful")
  void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    appUserRepository.save(USER);

    String expectedName = savedAnime.getName();

    PageableResponse<Anime> animePage = testRestTemplateRoleUser.exchange(
        "/animes", HttpMethod.GET, null,
        new ParameterizedTypeReference<PageableResponse<Anime>>() {
        }).getBody();

    Assertions.assertThat(animePage).isNotNull();
    Assertions.assertThat(animePage.toList())
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  @DisplayName("listAll returns list of animes when successful")
  void listAll_ReturnsListOfAnimes_WhenSuccessful() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    appUserRepository.save(USER);

    String expectedName = savedAnime.getName();

    List<Anime> animes = testRestTemplateRoleUser.exchange(
        "/animes/all", HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Anime>>() {
        }).getBody();

    Assertions.assertThat(animes)
        .isNotNull()
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  @DisplayName("findById returns anime when successful")
  void findById_ReturnsAnime_WhenSuccessful() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    appUserRepository.save(USER);

    Long expectedId = savedAnime.getId();

    Anime anime = testRestTemplateRoleUser.getForObject("/animes/{id}", Anime.class, expectedId);

    Assertions.assertThat(anime).isNotNull();
    Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
  }

  @Test
  @DisplayName("findByName returns list of anime when successful")
  void findByName_ReturnsListOfAnime_WhenSuccessful() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    appUserRepository.save(USER);

    String expectedName = savedAnime.getName();

    String url = String.format("/animes/find?name=%s", expectedName);
    List<Anime> animes = testRestTemplateRoleUser.exchange(
        url, HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Anime>>() {
        }).getBody();

    Assertions.assertThat(animes)
        .isNotNull()
        .isNotEmpty()
        .hasSize(1);
    Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
  }

  @Test
  @DisplayName("findByName returns empty list of anime when anime is not found")
  void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
    appUserRepository.save(USER);

    List<Anime> animes = testRestTemplateRoleUser.exchange(
        "/animes/find?name=dbz", HttpMethod.GET, null,
        new ParameterizedTypeReference<List<Anime>>() {
        }).getBody();

    Assertions.assertThat(animes)
        .isNotNull()
        .isEmpty();
  }

  @Test
  @DisplayName("save returns anime when successful")
  void save_ReturnsAnime_WhenSuccessful() {
    appUserRepository.save(USER);

    AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
    ResponseEntity<Anime> animeResponseEntity = testRestTemplateRoleUser.postForEntity(
        "/animes", animePostRequestBody, Anime.class
    );

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
    Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
  }

  @Test
  @DisplayName("replace updates anime when successful")
  void replace_UpdatesAnime_WhenSuccessful() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    savedAnime.setName("dbgt");
    appUserRepository.save(USER);

    ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange(
        "/animes", HttpMethod.PUT, new HttpEntity<>(savedAnime), Void.class
    );

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  @DisplayName("delete removes anime when successful")
  void delete_RemovesAnime_WhenSuccessful() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    appUserRepository.save(ADMIN);

    ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleAdmin.exchange(
        "/animes/admin/{id}", HttpMethod.DELETE, null, Void.class, savedAnime.getId()
    );

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  @DisplayName("delete returns 403 when user is not admin")
  void delete_Returns403_WhenUserIsNotAdmin() {
    Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
    appUserRepository.save(USER);

    ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange(
        "/animes/admin/{id}", HttpMethod.DELETE, null, Void.class, savedAnime.getId()
    );

    Assertions.assertThat(animeResponseEntity).isNotNull();
    Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

}