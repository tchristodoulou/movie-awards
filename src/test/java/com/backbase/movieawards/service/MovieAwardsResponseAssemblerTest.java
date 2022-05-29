package com.backbase.movieawards.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backbase.movieawards.respository.entity.MovieDetailsFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieAwardsResponseAssemblerTest {

  private MovieAwardsResponseAssembler subject;

  @BeforeEach
  void onBeforeEach() {
    subject = new MovieAwardsResponseAssembler();
  }

  @Test
  void assemble_WithMovieDetails_ReturnsMovieDetailsResponse() {
    final var movieDetails = MovieDetailsFixture.getCompletedMovieDetails();
    final var movieDetailsResponse = subject.assemble(movieDetails);
    assertEquals(movieDetails.getId(), movieDetailsResponse.getId());
    assertEquals(movieDetails.getYear(), movieDetailsResponse.getYear());
    assertEquals(movieDetails.getCategory(), movieDetailsResponse.getCategory());
    assertEquals(movieDetails.getNominee(), movieDetailsResponse.getNominee());
    assertEquals(movieDetails.getAdditionalInfo(), movieDetailsResponse.getAdditionalInfo());
    assertEquals(movieDetails.getWon(), movieDetailsResponse.getWon());
  }
}