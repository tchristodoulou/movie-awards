package com.backbase.movieawards.service;

import com.backbase.movieawards.model.MovieReviewRequest;
import com.backbase.movieawards.repository.entity.MovieReview;
import org.springframework.stereotype.Component;

@Component
public class MovieReviewAssembler {

  public MovieReview assemble(final MovieReviewRequest movieReviewRequest) {
    return MovieReview.builder()
        .title(movieReviewRequest.getTitle())
        .movieYear(movieReviewRequest.getMovieYear())
        .reviewScore(movieReviewRequest.getReviewScore())
        .build();
  }
}
