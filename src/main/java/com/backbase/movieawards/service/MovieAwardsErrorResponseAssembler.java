package com.backbase.movieawards.service;

import com.backbase.movieawards.model.MovieAwardsErrorResponse;
import org.springframework.stereotype.Component;

@Component
public class MovieAwardsErrorResponseAssembler {

  public MovieAwardsErrorResponse assemble(final String field, final String error) {
    return MovieAwardsErrorResponse.builder()
        .field(field)
        .error(error)
        .build();
  }
}
