package com.backbase.movieawards.service;

import com.backbase.movieawards.model.OMDBMovieDetailsResponse;
import com.backbase.movieawards.repository.MovieDetailsRepository;
import com.backbase.movieawards.repository.entity.MovieDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MovieAwardsService {

  private static final String BEST_PICTURE = "Best Picture";

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

    final var response = restTemplate.getForEntity(urlTemplate.toUriString(), OMDBMovieDetailsResponse.class);

    if(!HttpStatus.OK.equals(response.getStatusCode())) {
      // Throw exception to handle and return bad request / other errors
    }

    final var movieDetailsResponse = response.getBody();

    final var movieDetails = movieDetailsRepository.findByNomineeAndYearAndCategory(movieDetailsResponse.getTitle(), movieDetailsResponse.getYear(), BEST_PICTURE);

    return movieDetails;
  }
}
