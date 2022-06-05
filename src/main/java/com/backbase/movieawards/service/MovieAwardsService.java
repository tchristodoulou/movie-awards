package com.backbase.movieawards.service;

import com.backbase.movieawards.model.OMDBMovieDetailsResponse;
import com.backbase.movieawards.repository.MovieDetailsRepository;
import com.backbase.movieawards.repository.entity.MovieDetails;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class MovieAwardsService {

  private static final String BEST_PICTURE = "Best Picture";
  private static final String WON_NO = "NO";
  private static final String OMDB_NOT_FOUND = "No title [%s] found for year [%s]";
  private static final String NOT_NOMINATED = "Not nominated for Best Picture";

  private final RestTemplate restTemplate;
  private final MovieDetailsRepository movieDetailsRepository;

  @Value("${movie-awards.omdb-api-key}")
  private String apiKey;

  @Value("${movie-awards.omdb-api-url}")
  private String baseUrl;

  public MovieDetails getMovieDetails(final String name, final String year) {
    final var urlTemplate = UriComponentsBuilder.fromHttpUrl(baseUrl)
        .queryParam("apikey", apiKey)
        .queryParam("t", name.replace(" ", "+"))
        .queryParam("y", year);

    final var stopwatch = new StopWatch();
    stopwatch.start();

    log.info("Sending request to url [{}]", urlTemplate.toUriString());
    final var response = restTemplate.getForEntity(urlTemplate.toUriString(),
        OMDBMovieDetailsResponse.class);

    stopwatch.stop();
    log.info("Response received, time taken {}ms", stopwatch.getTotalTimeMillis());

    if (checkResponse(response)) {
      log.debug("No movie found for title [{}] and year [{}]", name, year);
      throw new MovieNotFoundException(String.format(OMDB_NOT_FOUND, name, year));
    }

    final var movieDetailsResponse = response.getBody();

    final var movieDetails = movieDetailsRepository.findByNomineeAndMovieYearAndCategory(
        movieDetailsResponse.getTitle(), movieDetailsResponse.getYear(), BEST_PICTURE);

    return movieDetails.orElse(createMovieDetails(movieDetailsResponse));
  }

  private boolean checkResponse(final ResponseEntity<OMDBMovieDetailsResponse> response) {
    return Objects.isNull(response) || Objects.isNull(response.getBody()) || Objects.isNull(
        response.getBody().getTitle());
  }

  private MovieDetails createMovieDetails(final OMDBMovieDetailsResponse response) {
    log.debug("The movie [{}] and year [{}] was not nominated for best movie", response.getTitle(), response.getYear());
    return MovieDetails.builder()
        .id(null)
        .nominee(response.getTitle())
        .movieYear(response.getYear())
        .category(BEST_PICTURE)
        .additionalInfo(NOT_NOMINATED)
        .won(WON_NO)
        .build();
  }
}
