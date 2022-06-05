package com.backbase.movieawards.respository.entity;

import com.backbase.movieawards.repository.entity.MovieDetails;
import java.util.UUID;

public class MovieDetailsFixture {

  public static MovieDetails getCompletedMovieDetails() {
    return MovieDetails.builder()
        .id(UUID.randomUUID())
        .movieYear("2022")
        .category("test category")
        .nominee("mr test")
        .additionalInfo("test")
        .won("NO")
        .build();
  }

  public static MovieDetails getWinningCompletedMovieDetails() {
    return MovieDetails.builder()
        .id(UUID.randomUUID())
        .movieYear("2022")
        .category("Best Picture")
        .nominee("test")
        .additionalInfo("test")
        .won("YES")
        .build();
  }
}
