package com.backbase.movieawards.service;

import com.backbase.movieawards.repository.entity.MovieDetails;
import com.backbase.movieawards.repository.entity.WinningTitle;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class MovieAwardsService {

  public MovieDetails getMovieDetails() {
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
