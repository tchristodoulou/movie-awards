package com.backbase.movieawards.repository.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "movie_reviews")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieReview {

  @Id
  @GeneratedValue
  @Type(type = "uuid-char")
  @Column(name = "id", nullable = false, length = 36)
  private UUID id;

  @Column(name = "movie_year")
  private String movieYear;

  @Column(name = "title")
  private String title;

  @Column(name = "review_score")
  private int reviewScore;
}
