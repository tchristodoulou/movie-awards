package com.backbase.movieawards.respository.entity;

import com.backbase.movieawards.repository.entity.MovieReview;

public class MovieReviewFixture {

  public static MovieReview getCompletedMovieReview() {
    return MovieReview.builder()
        .title("test movie")
        .movieYear("2022")
        .reviewScore(90)
        .build();
  }
}
