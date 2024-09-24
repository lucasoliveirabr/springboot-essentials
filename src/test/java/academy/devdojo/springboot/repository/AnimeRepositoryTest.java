package academy.devdojo.springboot.repository;

import academy.devdojo.springboot.domain.Anime;
import academy.devdojo.springboot.util.AnimeCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

  @Autowired
  private AnimeRepository animeRepository;

  @Test
  @DisplayName("save persists anime when Successful")
  void save_PersistsAnime_WhenSuccessful() {
    Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
    Anime animeSaved = this.animeRepository.save(animeToBeSaved);

    Assertions.assertThat(animeSaved).isNotNull();
    Assertions.assertThat(animeSaved.getId()).isNotNull();
    Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
  }

  @Test
  @DisplayName("save updates anime when Successful")
  void save_UpdatesAnime_WhenSuccessful() {
    Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
    Anime animeSaved = this.animeRepository.save(animeToBeSaved);
    animeSaved.setName("Overlord");
    Anime animeUpdated = this.animeRepository.save(animeSaved);

    Assertions.assertThat(animeUpdated).isNotNull();
    Assertions.assertThat(animeUpdated.getId()).isNotNull();
    Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
  }

  @Test
  @DisplayName("delete removes anime when Successful")
  void delete_RemovesAnime_WhenSuccessful() {
    Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
    Anime animeSaved = this.animeRepository.save(animeToBeSaved);
    this.animeRepository.delete(animeSaved);
    Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

    Assertions.assertThat(animeOptional).isEmpty();
  }

  @Test
  @DisplayName("findByName returns list of anime when Successful")
  void findByName_ReturnsListOfAnime_WhenSuccessful() {
    Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
    Anime animeSaved = this.animeRepository.save(animeToBeSaved);
    String name = animeSaved.getName();
    List<Anime> animes = this.animeRepository.findByName(name);

    Assertions.assertThat(animes)
        .isNotEmpty()
        .contains(animeSaved);
  }

  @Test
  @DisplayName("findByName returns empty list when no anime is found")
  void findByName_ReturnsEmptyList_WhenNoAnimeIsFound() {
    Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
    this.animeRepository.save(animeToBeSaved);
    String name = "Netflix";
    List<Anime> animes = this.animeRepository.findByName(name);

    Assertions.assertThat(animes).isEmpty();
  }

  @Test
  @DisplayName("save throws ConstraintViolationException when name is empty")
  void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
    Anime anime = new Anime();

    // Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
    //     .isInstanceOf(ConstraintViolationException.class);

    Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
        .isThrownBy(() -> this.animeRepository.save(anime))
        .withMessageContaining("The anime name cannot be empty, null or blank space.");
  }

}