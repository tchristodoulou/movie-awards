package com.backbase.movieawards.service;

import com.backbase.movieawards.model.MovieReviewResponse;
import com.backbase.movieawards.repository.entity.MovieReview;
import org.springframework.stereotype.Component;

@Component
public class MovieReviewResponseAssembler {

  public MovieReviewResponse assemble(final MovieReview movieReview) {
    return MovieReviewResponse.builder()
        .id(movieReview.getId())
        .title(movieReview.getTitle())
        .movieYear(movieReview.getMovieYear())
        .reviewScore(movieReview.getReviewScore())
        .build();
  }
}
