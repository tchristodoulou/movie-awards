package com.backbase.movieawards.model;

public class OMDBMovieDetailsResponseFixtures {

  public static OMDBMovieDetailsResponse getOMDBMovieDetailsResponse() {
    return OMDBMovieDetailsResponse.builder()
        .title("test")
        .year("2022")
        .plot("test plot")
        .awards("test award")
        .boxOffice("$1")
        .build();
  }
}
