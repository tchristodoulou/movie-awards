package com.backbase.movieawards.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieReviewRequest {

  @NotBlank
  @Length(max = 4, min = 4)
  private String movieYear;

  @NotBlank
  @Length(max = 255)
  private String title;

  @NotNull
  @Min(0)
  @Max(100)
  private Integer reviewScore;
}
