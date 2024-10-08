package academy.devdojo.springboot.service;

import academy.devdojo.springboot.domain.Anime;
import academy.devdojo.springboot.exception.BadRequestException;
import academy.devdojo.springboot.mapper.AnimeMapper;
import academy.devdojo.springboot.repository.AnimeRepository;
import academy.devdojo.springboot.requests.AnimePostRequestBody;
import academy.devdojo.springboot.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
  private final AnimeRepository animeRepository;

  public Page<Anime> listAll(Pageable pageable) {
    return animeRepository.findAll(pageable);
  }

  public List<Anime> listAllNonPageable() {
    return animeRepository.findAll();
  }

  public List<Anime> findByName(String name) {
    return animeRepository.findByName(name);
  }

  public Anime findByIdOrThrowBadRequestException(long id) {
    return animeRepository.findById(id)
        .orElseThrow(() -> new BadRequestException("Anime not found."));
  }

  @Transactional
  public Anime save(AnimePostRequestBody animePostRequestBody) {
    Anime anime = AnimeMapper.INSTANCE.toAnime(animePostRequestBody);
    return animeRepository.save(anime);
  }

  public void delete(long id) {
    animeRepository.delete(findByIdOrThrowBadRequestException(id));
  }

  public void replace(AnimePutRequestBody animePutRequestBody) {
    Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
    Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
    anime.setId(savedAnime.getId());
    animeRepository.save(anime);
  }
}