package com.backbase.movieawards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.backbase.movieawards.model.MovieReviewRequest;
import com.backbase.movieawards.model.MovieReviewRequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieReviewAssemblerTest {

  private MovieReviewRequest movieReviewRequest;

  private MovieReviewAssembler subject;

  @BeforeEach
  void onBeforeEach() {
    movieReviewRequest = MovieReviewRequestFixture.getCompletedMovieReviewRequest();
    subject = new MovieReviewAssembler();
  }

  @Test
  void assembler_WithMovieReviewRequest_ReturnsMovieReview() {
    final var movieReview = subject.assemble(movieReviewRequest);
    assertNotNull(movieReview);
    assertEquals(movieReviewRequest.getTitle(), movieReview.getTitle());
    assertEquals(movieReviewRequest.getMovieYear(), movieReview.getMovieYear());
    assertEquals(movieReviewRequest.getReviewScore(), movieReview.getReviewScore());
  }
}