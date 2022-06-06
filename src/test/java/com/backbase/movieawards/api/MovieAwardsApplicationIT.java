package com.backbase.movieawards.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.backbase.movieawards.model.MovieAwardsErrorResponse;
import com.backbase.movieawards.model.MovieDetailsResponse;
import com.backbase.movieawards.model.MovieReviewRequestFixture;
import com.backbase.movieawards.model.MovieReviewResponse;
import com.backbase.movieawards.model.OMDBMovieDetailsResponse;
import com.backbase.movieawards.model.OMDBMovieDetailsResponseFixtures;
import com.backbase.movieawards.repository.MovieDetailsRepository;
import com.backbase.movieawards.repository.MovieReviewRepository;
import com.backbase.movieawards.respository.entity.MovieDetailsFixture;
import java.util.Arrays;
import java.util.Comparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MovieAwardsApplicationIT {

  @Autowired
  private MovieDetailsRepository movieDetailsRepository;

  @Autowired
  private MovieReviewRepository movieReviewRepository;

  @LocalServerPort
  private int randomServerPort;

  private static final String BEST_PICTURE_URL = "http://localhost:%d/movie-awards/best-picture";
  private static final String SAVE_REVIEW_URL = "http://localhost:%d/movie-awards/review";
  private static final String API_KEY = "testKey";
  private static final String NAME = "test name";
  private static final String YEAR = "2022";
  private static final String OMDB_NOT_FOUND_ERROR_MESSAGE = "No title [test name] found for year [2022]";
  private static final String WON_NO = "NO";
  private static final String NOT_NOMINATED = "Not nominated for Best Picture";
  private static final String BEST_PICTURE = "Best Picture";

  @MockBean
  private RestTemplate mockedRestTemplate;

  private TestRestTemplate testRestTemplate;

  @BeforeEach
  void onBeforeEach() {
    testRestTemplate = new TestRestTemplate();

    movieDetailsRepository.deleteAll();
  }

  @Test
  void getBestPicture_WithValidRequest_ReturnsOkResponseWithMovieDetails() {
    final var omdbMovieDetailsResponse = OMDBMovieDetailsResponseFixtures.getOMDBMovieDetailsResponse();
    final var movieDetails = MovieDetailsFixture.getWinningCompletedMovieDetails();

    final var savedMovieDetails = movieDetailsRepository.save(movieDetails);

    final var omdbUrlTemplate = UriComponentsBuilder.fromHttpUrl("http://www.testapi.com/")
        .queryParam("apikey", API_KEY)
        .queryParam("t", NAME.replace(" ", "_"))
        .queryParam("y", YEAR);

    when(mockedRestTemplate.getForEntity(omdbUrlTemplate.toUriString(),
        OMDBMovieDetailsResponse.class)).thenReturn(ResponseEntity.ok(omdbMovieDetailsResponse));

    final var urlTemplate = UriComponentsBuilder.fromHttpUrl(String.format(BEST_PICTURE_URL, randomServerPort))
        .queryParam("name", NAME.replace(" ", "_"))
        .queryParam("year", YEAR);

    final var response = testRestTemplate.getForEntity(urlTemplate.toUriString(),
        MovieDetailsResponse.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    final var movieDetailsResponse = response.getBody();
    assertNotNull(movieDetailsResponse);
    assertEquals(savedMovieDetails.getId(), movieDetailsResponse.getId());
    assertEquals(savedMovieDetails.getMovieYear(), movieDetailsResponse.getYear());
    assertEquals(savedMovieDetails.getCategory(), movieDetailsResponse.getCategory());
    assertEquals(savedMovieDetails.getNominee(), movieDetailsResponse.getNominee());
    assertEquals(savedMovieDetails.getAdditionalInfo(), movieDetailsResponse.getAdditionalInfo());
    assertEquals(savedMovieDetails.getWon(), movieDetailsResponse.getWon());
  }

  @Test
  void getBestPicture_WithInvalidMovieAndYear_ReturnsNotFoundWithErrorMessage() {
    final var omdbUrlTemplate = UriComponentsBuilder.fromHttpUrl("http://www.testapi.com/")
        .queryParam("apikey", API_KEY)
        .queryParam("t", NAME.replace(" ", "+"))
        .queryParam("y", YEAR);

    when(mockedRestTemplate.getForEntity(omdbUrlTemplate.toUriString(),
        OMDBMovieDetailsResponse.class)).thenReturn(ResponseEntity.ok(null));

    final var urlTemplate = UriComponentsBuilder.fromHttpUrl(String.format(BEST_PICTURE_URL, randomServerPort))
        .queryParam("name", NAME.replace(" ", "+"))
        .queryParam("year", YEAR);

    final var response = testRestTemplate.getForEntity(urlTemplate.toUriString(), String.class);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    final var errorMessage = response.getBody();
    assertEquals(OMDB_NOT_FOUND_ERROR_MESSAGE, errorMessage);
  }

  @Test
  void getBestPicture_WithValidRequestButNotInDatabase_ReturnsOkResponseWithMovieDetails() {
    final var restTemplate = new TestRestTemplate();
    final var omdbMovieDetailsResponse = OMDBMovieDetailsResponseFixtures.getOMDBMovieDetailsResponse();

    final var omdbUrlTemplate = UriComponentsBuilder.fromHttpUrl("http://www.testapi.com/")
        .queryParam("apikey", API_KEY)
        .queryParam("t", NAME.replace(" ", "_"))
        .queryParam("y", YEAR);

    when(mockedRestTemplate.getForEntity(omdbUrlTemplate.toUriString(),
        OMDBMovieDetailsResponse.class)).thenReturn(ResponseEntity.ok(omdbMovieDetailsResponse));

    final var urlTemplate = UriComponentsBuilder.fromHttpUrl(String.format(BEST_PICTURE_URL, randomServerPort))
        .queryParam("name", NAME.replace(" ", "_"))
        .queryParam("year", YEAR);

    final var response = restTemplate.getForEntity(urlTemplate.toUriString(),
        MovieDetailsResponse.class);
    assertNotNull(response);
    assertEquals(HttpStatus.OK, response.getStatusCode());

    final var movieDetailsResponse = response.getBody();
    assertNotNull(movieDetailsResponse);
    assertNull(movieDetailsResponse.getId());
    assertEquals(omdbMovieDetailsResponse.getYear(), movieDetailsResponse.getYear());
    assertEquals(BEST_PICTURE, movieDetailsResponse.getCategory());
    assertEquals(omdbMovieDetailsResponse.getTitle(), movieDetailsResponse.getNominee());
    assertEquals(NOT_NOMINATED, movieDetailsResponse.getAdditionalInfo());
    assertEquals(WON_NO, movieDetailsResponse.getWon());
  }

  @Test
  void getBestPicture_WithInvalidRequest_ReturnsBadRequestWithErrors() {
    final var urlTemplate = UriComponentsBuilder.fromHttpUrl(String.format(BEST_PICTURE_URL, randomServerPort))
        .queryParam("name", "")
        .queryParam("year", "");;

    final var response = testRestTemplate.getForEntity(urlTemplate.toUriString(),
        MovieAwardsErrorResponse[].class);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    final var errors = Arrays.asList(response.getBody());
    assertNotNull(errors);
    assertEquals(2, errors.size());

    final var iterator = errors.stream().sorted(Comparator.comparing(p -> p.hashCode())).iterator();

    final var error1 = iterator.next();
    assertEquals("name", (error1).getField());
    assertEquals("must not be blank", (error1).getError());

    final var error2 = iterator.next();
    assertEquals("year", (error2).getField());
    assertEquals("must not be blank", (error2).getError());
  }

  @Test
  void saveReview_WithMovieReviewRequest_ReturnsCreatedWithMovieReviewResponse() {
    final var movieReviewRequest = MovieReviewRequestFixture.getCompletedMovieReviewRequest();

    final var urlTemplate = UriComponentsBuilder.fromHttpUrl(String.format(SAVE_REVIEW_URL, randomServerPort));

    final var response = testRestTemplate.postForEntity(urlTemplate.toUriString(),movieReviewRequest,
        MovieReviewResponse.class);
    assertNotNull(response);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    final var movieReviewResponse = response.getBody();
    assertNotNull(movieReviewResponse);
    assertEquals(movieReviewRequest.getTitle(), movieReviewResponse.getTitle());
    assertEquals(movieReviewRequest.getMovieYear(), movieReviewResponse.getMovieYear());
    assertEquals(movieReviewRequest.getReviewScore(), movieReviewResponse.getReviewScore());

    final var movieReview = movieReviewRepository.findById(movieReviewResponse.getId());
    assertTrue(movieReview.isPresent());
    final var savedMovieReview = movieReview.get();
    assertEquals(movieReviewRequest.getTitle(), savedMovieReview.getTitle());
    assertEquals(movieReviewRequest.getMovieYear(), savedMovieReview.getMovieYear());
    assertEquals(movieReviewRequest.getReviewScore(), savedMovieReview.getReviewScore());
  }

  /*
  TODO: more tests for the negative cases on saveReview
   */
}
