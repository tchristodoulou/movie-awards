package com.backbase.movieawards.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OMDBMovieDetailsResponse {

  @JsonProperty("Title")
  private String title;

  @JsonProperty("Year")
  private String year;

  @JsonProperty("Plot")
  private String plot;

  @JsonProperty("Awards")
  private String awards;

  @JsonProperty("BoxOffice")
  private String boxOffice;
}
