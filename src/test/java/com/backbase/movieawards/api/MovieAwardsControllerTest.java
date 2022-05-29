package com.backbase.movieawards.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.backbase.movieawards.model.MovieDetailsResponse;
import com.backbase.movieawards.repository.entity.MovieDetails;
import com.backbase.movieawards.service.MovieAwardsResponseAssembler;
import com.backbase.movieawards.service.MovieAwardsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class MovieAwardsControllerTest {

  private static final String NAME = "test name";
  private static final String YEAR = "2022";

  @Mock private MovieAwardsService movieAwardsService;
  @Mock private MovieAwardsResponseAssembler movieAwardsResponseAssembler;
  @Mock private MovieDetails movieDetails;
  @Mock private MovieDetailsResponse movieDetailsResponse;

  private MovieAwardsController subject;

  @BeforeEach
  void onBeforeEach() {
    subject = new MovieAwardsController(movieAwardsService, movieAwardsResponseAssembler);
  }

  @Test
  void getBestPicture_returnsOkStatusWithMovieDetailsResponse() {
    when(movieAwardsService.getMovieDetails(NAME, YEAR)).thenReturn(movieDetails);
    when(movieAwardsResponseAssembler.assemble(movieDetails)).thenReturn(movieDetailsResponse);
    final var response = subject.getBestPicture(NAME, YEAR);
    assertNotNull(movieDetailsResponse);
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(movieDetailsResponse, response.getBody());
  }
}