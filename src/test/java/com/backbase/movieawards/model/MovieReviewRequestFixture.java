package com.backbase.movieawards.model;

public class MovieReviewRequestFixture {

  public static MovieReviewRequest getCompletedMovieReviewRequest() {
    return MovieReviewRequest.builder()
        .title("test movie")
        .movieYear("2022")
        .reviewScore(90)
        .build();
  }
}
