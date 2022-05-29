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
@Table(name = "movie-awards")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDetails {

  @Id
  @GeneratedValue
  @Type(type = "uuid-char")
  @Column(name = "id", nullable = false, length = 36)
  private UUID id;

  @Column(name = "year", length = 4)
  private String year;

  @Column(name = "category", length = 50)
  private String category;

  @Column(name = "nominee", length = 50)
  private String nominee;

  @Column(name = "additionalInfo", length = 50)
  private String additionalInfo;

  @Column(name = "won", length = 3)
  private WinningTitle won;
}
