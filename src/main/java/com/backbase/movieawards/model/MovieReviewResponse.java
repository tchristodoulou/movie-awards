package com.backbase.movieawards.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieReviewResponse {

  private UUID id;

  private String movieYear;

  private String title;

  private int reviewScore;
}
