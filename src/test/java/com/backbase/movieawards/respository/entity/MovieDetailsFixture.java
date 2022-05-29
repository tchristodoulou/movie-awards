package com.backbase.movieawards.respository.entity;

import com.backbase.movieawards.repository.entity.MovieDetails;
import com.backbase.movieawards.repository.entity.WinningTitle;
import java.util.UUID;

public class MovieDetailsFixture {

  public static MovieDetails getCompletedMovieDetails() {
    return MovieDetails.builder()
        .id(UUID.randomUUID())
        .year("2022")
        .category("test category")
        .nominee("mr test")
        .additionalInfo("test")
        .won(WinningTitle.NO)
        .build();
  }
}
