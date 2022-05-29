package com.backbase.movieawards.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieAwardsServiceTest {

  private MovieAwardsService subject;

  @BeforeEach
  void onBeforeEach() {
    subject = new MovieAwardsService();
  }

  @Test
  void getMovieDetails_ReturnsMovieDetails() {
    final var movieDetails = subject.getMovieDetails();
    assertNotNull(movieDetails);
  }
}