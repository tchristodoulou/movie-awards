package com.backbase.movieawards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.backbase.movieawards.model.OMDBMovieDetailsResponse;
import com.backbase.movieawards.model.OMDBMovieDetailsResponseFixtures;
import com.backbase.movieawards.repository.MovieDetailsRepository;
import com.backbase.movieawards.repository.entity.MovieDetails;
import com.backbase.movieawards.respository.entity.MovieDetailsFixture;
import java.util.Optional;
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
  private static final String API_KEY = "testKey";
  private static final String URL = "http://www.testapi.com/";
  private static final String TEST_URL = URL + "?apikey=" + API_KEY + "&t=test_name&y=" + YEAR;
  private static final String BEST_PICTURE = "Best Picture";
  private static final String OMDB_NOT_FOUND = "No title [test name] found for year [2022]";
  private static final String WON_NO = "NO";
  private static final String NOT_NOMINATED = "Not nominated for Best Picture";

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
        omdbMovieDetailsResponse.getYear(), BEST_PICTURE)).thenReturn(Optional.of(movieDetails));

    final var actualMovieDetails = subject.getMovieDetails(NAME, YEAR);
    assertEquals(movieDetails, actualMovieDetails);
  }

  @Test
  void getMovieDetails_WithNullResponse_ThrowsMovieNotFoundException() {
    ReflectionTestUtils.setField(subject, "apiKey", API_KEY);
    ReflectionTestUtils.setField(subject, "baseUrl", URL);

    when(restTemplate.getForEntity(TEST_URL, OMDBMovieDetailsResponse.class)).thenReturn(null);

    final var movieNotFound = assertThrows(MovieNotFoundException.class, () -> subject.getMovieDetails(NAME, YEAR));
    assertEquals(OMDB_NOT_FOUND, movieNotFound.getMessage());
    verifyNoInteractions(movieDetailsRepository);
  }

  @Test
  void getMovieDetails_WithNullResponseBody_ThrowsMovieNotFoundException() {
    ReflectionTestUtils.setField(subject, "apiKey", API_KEY);
    ReflectionTestUtils.setField(subject, "baseUrl", URL);

    when(restTemplate.getForEntity(TEST_URL, OMDBMovieDetailsResponse.class)).thenReturn(ResponseEntity.ok(null));

    final var movieNotFound = assertThrows(MovieNotFoundException.class, () -> subject.getMovieDetails(NAME, YEAR));
    assertEquals(OMDB_NOT_FOUND, movieNotFound.getMessage());
    verifyNoInteractions(movieDetailsRepository);
  }

  @Test
  void getMovieDetails_WithNullResponseBodyTitle_ThrowsMovieNotFoundException() {
    ReflectionTestUtils.setField(subject, "apiKey", API_KEY);
    ReflectionTestUtils.setField(subject, "baseUrl", URL);

    when(restTemplate.getForEntity(TEST_URL, OMDBMovieDetailsResponse.class)).thenReturn(ResponseEntity.ok(OMDBMovieDetailsResponse.builder()
        .build()));

    final var movieNotFound = assertThrows(MovieNotFoundException.class, () -> subject.getMovieDetails(NAME, YEAR));
    assertEquals(OMDB_NOT_FOUND, movieNotFound.getMessage());
    verifyNoInteractions(movieDetailsRepository);
  }

  @Test
  void getMovieDetails_WithNotNominatedMovie_ReturnsMovieDetailsAndWonSetToNo() {
    ReflectionTestUtils.setField(subject, "apiKey", API_KEY);
    ReflectionTestUtils.setField(subject, "baseUrl", URL);

    when(restTemplate.getForEntity(TEST_URL, OMDBMovieDetailsResponse.class)).thenReturn(ResponseEntity.ok(omdbMovieDetailsResponse));
    when(movieDetailsRepository.findByNomineeAndYearAndCategory(omdbMovieDetailsResponse.getTitle(),
        omdbMovieDetailsResponse.getYear(), BEST_PICTURE)).thenReturn(Optional.empty());

    final var actualMovieDetails = subject.getMovieDetails(NAME, YEAR);
    assertNotNull(actualMovieDetails);
    assertNull(actualMovieDetails.getId());
    assertEquals(omdbMovieDetailsResponse.getYear(), actualMovieDetails.getYear());
    assertEquals(omdbMovieDetailsResponse.getTitle(), actualMovieDetails.getNominee());
    assertEquals(BEST_PICTURE, actualMovieDetails.getCategory());
    assertEquals(NOT_NOMINATED, actualMovieDetails.getAdditionalInfo());
    assertEquals(WON_NO, movieDetails.getWon());
  }
}