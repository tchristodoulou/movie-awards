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
public class MovieDetailsResponse {

  private UUID id;

  private String year;

  private String category;

  private String nominee;

  private String additionalInfo;

  private String won;
}
