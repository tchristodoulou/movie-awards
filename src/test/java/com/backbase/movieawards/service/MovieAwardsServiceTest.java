package com.backbase.movieawards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.backbase.movieawards.model.OMDBMovieDetailsResponse;
import com.backbase.movieawards.model.OMDBMovieDetailsResponseFixtures;
import com.backbase.movieawards.repository.MovieDetailsRepository;
import com.backbase.movieawards.repository.entity.MovieDetails;
import com.backbase.movieawards.respository.entity.MovieDetailsFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class MovieAwardsServiceTest {

  private static final String NAME = "test name";
  private static final String YEAR = "2022";
  private static final String WON = "NO";
  private static final String API_KEY = "testKey";
  private static final String URL = "http://www.testapi.com/";
  private static final String TEST_URL = URL + "?apikey=" + API_KEY + "&t=test+name&y=" + YEAR;
  private static final String BEST_PICTURE = "Best Picture";

  @Mock private RestTemplate restTemplate;
  @Mock private MovieDetailsRepository movieDetailsRepository;

  private MovieAwardsService subject;
  private OMDBMovieDetailsResponse omdbMovieDetailsResponse;
  private MovieDetails movieDetails;

  @BeforeEach
  void onBeforeEach() {
    omdbMovieDetailsResponse = OMDBMovieDetailsResponseFixtures.getOMDBMovieDetailsResponse();
    movieDetails = MovieDetailsFixture.getCompletedMovieDetails();
    subject = new MovieAwardsService(restTemplate, movieDetailsRepository);
  }

  @Test
  void getMovieDetails_WithNameAndYear_ReturnsMovieDetails() {
    ReflectionTestUtils.setField(subject, "apiKey", API_KEY);
    ReflectionTestUtils.setField(subject, "baseUrl", URL);

    when(restTemplate.getForEntity(TEST_URL, OMDBMovieDetailsResponse.class)).thenReturn(ResponseEntity.ok(omdbMovieDetailsResponse));
    when(movieDetailsRepository.findByNomineeAndYearAndCategory(omdbMovieDetailsResponse.getTitle(),
        omdbMovieDetailsResponse.getYear(), BEST_PICTURE)).thenReturn(movieDetails);

    final var actualMovieDetails = subject.getMovieDetails(NAME, YEAR);
    assertEquals(movieDetails, actualMovieDetails);
  }
}