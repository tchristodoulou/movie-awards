package com.backbase.movieawards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.backbase.movieawards.model.OMDBMovieDetailsResponse;
import com.backbase.movieawards.model.OMDBMovieDetailsResponseFixtures;
import com.backbase.movieawards.repository.entity.WinningTitle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
  private static final String TEST_URL = URL + "?apikey=" + API_KEY + "&t=test+name&y=" + YEAR;

  @Mock private RestTemplate restTemplate;

  private MovieAwardsService subject;
  private OMDBMovieDetailsResponse omdbMovieDetailsResponse;

  @BeforeEach
  void onBeforeEach() {
    omdbMovieDetailsResponse = OMDBMovieDetailsResponseFixtures.getOMDBMovieDetailsResponse();
    subject = new MovieAwardsService(restTemplate);
  }

  @Test
  void getMovieDetails_WithNameAndYear_ReturnsMovieDetails() {
    ReflectionTestUtils.setField(subject, "apiKey", API_KEY);
    ReflectionTestUtils.setField(subject, "baseUrl", URL);

    Mockito.when(restTemplate.getForEntity(TEST_URL, OMDBMovieDetailsResponse.class)).thenReturn(ResponseEntity.ok(omdbMovieDetailsResponse));

    final var movieDetails = subject.getMovieDetails(NAME, YEAR);
    assertNotNull(movieDetails);
    assertNotNull(movieDetails.getId());
    assertEquals(omdbMovieDetailsResponse.getYear(), movieDetails.getYear());
    assertEquals(omdbMovieDetailsResponse.getTitle(), movieDetails.getNominee());
    assertEquals(omdbMovieDetailsResponse.getPlot(), movieDetails.getAdditionalInfo());
    assertEquals(WinningTitle.NO, movieDetails.getWon());
  }
}