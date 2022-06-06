package com.backbase.movieawards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.backbase.movieawards.repository.entity.MovieReview;
import com.backbase.movieawards.respository.entity.MovieReviewFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieReviewResponseAssemblerTest {

  private MovieReview movieReview;
  private MovieReviewResponseAssembler subject;

  @BeforeEach
  void onBeforeEach() {
    movieReview = MovieReviewFixture.getCompletedMovieReview();
    subject = new MovieReviewResponseAssembler();
  }

  @Test
  void assemble_WithMovieReview_ReturnsMovieReviewResponse() {
    final var movieReviewResponse = subject.assemble(movieReview);
    assertNotNull(movieReviewResponse);
    assertEquals(movieReview.getId(), movieReviewResponse.getId());
    assertEquals(movieReview.getTitle(), movieReviewResponse.getTitle());
    assertEquals(movieReview.getMovieYear(), movieReviewResponse.getMovieYear());
    assertEquals(movieReview.getReviewScore(), movieReviewResponse.getReviewScore());
  }
}