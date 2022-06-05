package com.backbase.movieawards.service;

import com.backbase.movieawards.model.MovieDetailsResponse;
import com.backbase.movieawards.repository.entity.MovieDetails;
import org.springframework.stereotype.Component;

@Component
public class MovieAwardsResponseAssembler {

  public MovieDetailsResponse assemble(final MovieDetails movieDetails) {
    return MovieDetailsResponse.builder()
        .id(movieDetails.getId())
        .year(movieDetails.getMovieYear())
        .category(movieDetails.getCategory())
        .nominee(movieDetails.getNominee())
        .additionalInfo(movieDetails.getAdditionalInfo())
        .won(movieDetails.getWon())
        .build();
  }
}
